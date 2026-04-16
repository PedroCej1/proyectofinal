import pandas as pd
import matplotlib.pyplot as plt
from db import get_engine
import os
from sqlalchemy import create_engine

engine = get_engine()

empleados = pd.read_sql("SELECT * FROM em_empleados", engine)
proyectos = pd.read_sql("SELECT * FROM pr_proyectos", engine)

from fastapi import FastAPI
from fastapi.responses import FileResponse

app = FastAPI()


# =========================
# CONFIG
# =========================

os.makedirs("graficas", exist_ok=True)

plt.rcParams["axes.prop_cycle"] = plt.cycler(color=["#1265ff"])

engine = create_engine(
    "mysql+pymysql://root:root@mysql:3306/PRACTICA"
)


# =========================
# MAIN FUNCTION
# =========================

def run_analytics():

    # =====================
    # EMPLEADOS
    # =====================

    empleados = pd.read_sql("SELECT * FROM em_empleados", engine)

    empleados["F_ALTA"] = pd.to_datetime(empleados["F_ALTA"])
    empleados["F_BAJA"] = pd.to_datetime(empleados["F_BAJA"])
    empleados["F_NACIMIENTO"] = pd.to_datetime(empleados["F_NACIMIENTO"])

    hoy = pd.Timestamp.today()

    empleados["años_empresa"] = (hoy - empleados["F_ALTA"]).dt.days / 365
    empleados["edad"] = (hoy - empleados["F_NACIMIENTO"]).dt.days / 365


    # TOP ANTIGUOS
    top_antiguos = empleados.sort_values("años_empresa", ascending=False).head(5)

    plt.figure(figsize=(10,5))
    plt.bar(top_antiguos["tx_nombre"], top_antiguos["años_empresa"])
    plt.title("Top 5 empleados más antiguos")
    plt.xticks(rotation=45)
    plt.savefig("graficas/top5emp-ant.png", bbox_inches="tight")
    plt.close()


    # TOP RECIENTES
    top_recientes = empleados.sort_values("F_ALTA", ascending=False).head(5)

    plt.figure(figsize=(10,5))
    plt.bar(top_recientes["tx_nombre"], top_recientes["años_empresa"])
    plt.title("Top 5 empleados más recientes")
    plt.xticks(rotation=45)
    plt.savefig("graficas/top5emp-rec.png", bbox_inches="tight")
    plt.close()


    # DISTRIBUCIÓN AÑOS EMPRESA
    plt.figure(figsize=(10,5))
    plt.hist(empleados["años_empresa"], bins=10)
    plt.title("Distribución años en empresa")
    plt.savefig("graficas/dist-anyo.png", bbox_inches="tight")
    plt.close()


    # ALTAS
    altas = empleados["F_ALTA"].dt.year.value_counts().sort_index()

    plt.figure(figsize=(10,5))
    altas.plot(kind="line", marker="o")
    plt.title("Altas por año")
    plt.savefig("graficas/altas-anyo.png", bbox_inches="tight")
    plt.close()


    # BAJAS
    bajas = empleados["F_BAJA"].dropna().dt.year.value_counts().sort_index()

    plt.figure(figsize=(10,5))
    bajas.plot(kind="line", marker="o")
    plt.title("Bajas por año")
    plt.savefig("graficas/bajas-anyo.png", bbox_inches="tight")
    plt.close()


    # CRECIMIENTO
    crecimiento = (altas - bajas).fillna(0)

    plt.figure(figsize=(12,5))
    crecimiento.plot(kind="bar")
    plt.title("Crecimiento empleados por año")
    plt.savefig("graficas/emp-anyo.png", bbox_inches="tight")
    plt.close()


    
    # ESTADO CIVIL
    estado = empleados["cx_edocivil"].value_counts()

    plt.figure(figsize=(6,6))

    plt.pie(
        estado,
        labels=estado.index,
        autopct="%1.1f%%",
        colors=["#1265ff", "#8bb6ff"],  # dos colores
        wedgeprops={"edgecolor": "white"}
    )

    plt.title("Estado civil")
    plt.savefig("graficas/ecivil.png", bbox_inches="tight")
    plt.close()


    # EDAD
    plt.figure(figsize=(10,5))
    plt.hist(empleados["edad"], bins=10)
    plt.title("Distribución edades")
    plt.savefig("graficas/dist-edad.png", bbox_inches="tight")
    plt.close()


    # =====================
    # PROYECTOS
    # =====================

    df_proyectos = pd.read_sql("SELECT * FROM pr_proyectos", engine)

    df_proyectos["F_INICIO"] = pd.to_datetime(df_proyectos["F_INICIO"])
    df_proyectos["F_FIN"] = pd.to_datetime(df_proyectos["F_FIN"])
    df_proyectos["F_BAJA"] = pd.to_datetime(df_proyectos["F_BAJA"])


    total = df_proyectos.shape[0]

    activos = df_proyectos[
        df_proyectos["F_FIN"].isna() &
        df_proyectos["F_BAJA"].isna()
    ].shape[0]

    finalizados = df_proyectos[df_proyectos["F_FIN"].notna()].shape[0]


    plt.figure()
    plt.bar(["Total", "Activos", "Finalizados"], [total, activos, finalizados])
    plt.title("Resumen proyectos")
    plt.savefig("graficas/proy.png", bbox_inches="tight")
    plt.close()


    # DURACIÓN
    df_proyectos["duracion"] = (
        df_proyectos["F_FIN"] - df_proyectos["F_INICIO"]
    ).dt.days

    df_proyectos["duracion"] = df_proyectos["duracion"].fillna(
        (pd.Timestamp("today") - df_proyectos["F_INICIO"]).dt.days
    )

    plt.figure()
    plt.hist(df_proyectos["duracion"], bins=10)
    plt.title("Duración proyectos")
    plt.savefig("graficas/proy-durac.png", bbox_inches="tight")
    plt.close()


    # LUGAR
    proyectos_lugar = df_proyectos["TX_LUGAR"].value_counts()

    plt.figure()
    proyectos_lugar.plot(kind="bar")
    plt.title("Proyectos por lugar")
    plt.savefig("graficas/proy-lugar.png", bbox_inches="tight")
    plt.close()


    # =====================
    # RELACIÓN EMPLEADO-PROYECTO
    # =====================

    df_rel = pd.read_sql("SELECT * FROM pr_empleados_proyecto", engine)

    activos_emp = empleados[empleados["F_BAJA"].isna()]

    conteo = df_rel.groupby("ID_EMPLEADO").size()

    dist = activos_emp[["ID_EMPLEADO"]].merge(
        conteo.rename("num_proyectos"),
        left_on="ID_EMPLEADO",
        right_index=True,
        how="left"
    ).fillna(0)

    resumen = dist["num_proyectos"].value_counts().sort_index()

    plt.figure()
    resumen.plot(kind="bar")
    plt.title("Proyectos por empleado")
    plt.savefig("graficas/proy-emp.png", bbox_inches="tight")
    plt.close()


    # TOP PROYECTOS
    top_proyectos = df_rel.groupby("ID_PROYECTO").size().sort_values(ascending=False).head(5)

    top_proyectos = top_proyectos.reset_index(name="num_empleados")

    top_proyectos = top_proyectos.merge(
        df_proyectos[["ID_PROYECTO", "TX_DESCRIPCION"]],
        on="ID_PROYECTO"
    )

    plt.figure()
    plt.bar(top_proyectos["TX_DESCRIPCION"], top_proyectos["num_empleados"])
    plt.xticks(rotation=45)
    plt.title("Top proyectos con más empleados")
    plt.savefig("graficas/proymasemp.png", bbox_inches="tight")
    plt.close()


    return {
        "status": "ok",
        "graficas_generadas": 12
    }


@app.get("/run-analytics")
def run():
    return run_analytics()

# =========================
# EMPLEADOS
# =========================

@app.get("/grafica/altas-anyo")
def altas_anyo():
    return FileResponse("graficas/altas-anyo.png", media_type="image/png")


@app.get("/grafica/bajas-anyo")
def bajas_anyo():
    return FileResponse("graficas/bajas-anyo.png", media_type="image/png")


@app.get("/grafica/dist-anyo")
def dist_anyo():
    return FileResponse("graficas/dist-anyo.png", media_type="image/png")


@app.get("/grafica/dist-edad")
def dist_edad():
    return FileResponse("graficas/dist-edad.png", media_type="image/png")


@app.get("/grafica/ecivil")
def estado_civil():
    return FileResponse("graficas/ecivil.png", media_type="image/png")


@app.get("/grafica/emp-anyo")
def emp_anyo():
    return FileResponse("graficas/emp-anyo.png", media_type="image/png")


@app.get("/grafica/top5emp-ant")
def top5_ant():
    return FileResponse("graficas/top5emp-ant.png", media_type="image/png")


@app.get("/grafica/top5emp-rec")
def top5_rec():
    return FileResponse("graficas/top5emp-rec.png", media_type="image/png")


# =========================
# PROYECTOS
# =========================

@app.get("/grafica/proy-durac")
def proy_durac():
    return FileResponse("graficas/proy-durac.png", media_type="image/png")


@app.get("/grafica/proy-emp")
def proy_emp():
    return FileResponse("graficas/proy-emp.png", media_type="image/png")


@app.get("/grafica/proy-lugar")
def proy_lugar():
    return FileResponse("graficas/proy-lugar.png", media_type="image/png")


@app.get("/grafica/proy")
def proy():
    return FileResponse("graficas/proy.png", media_type="image/png")


@app.get("/grafica/proymasemp")
def proy_mas_emp():
    return FileResponse("graficas/proymasemp.png", media_type="image/png")
