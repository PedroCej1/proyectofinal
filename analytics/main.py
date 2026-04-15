import pandas as pd
import matplotlib.pyplot as plt
from db import get_engine

engine = get_engine()

empleados = pd.read_sql("SELECT * FROM em_empleados", engine)
proyectos = pd.read_sql("SELECT * FROM pr_proyectos", engine)


print(empleados.to_string())