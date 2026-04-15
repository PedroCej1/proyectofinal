package pedro.proyectofinal.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pedro.proyectofinal.model.Empleado;
import pedro.proyectofinal.model.EmpleadoProyecto;
import pedro.proyectofinal.model.Proyecto;
import pedro.proyectofinal.repository.EmpleadoProyectoRepository;
import pedro.proyectofinal.repository.EmpleadoRepository;
import pedro.proyectofinal.repository.ProyectoRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DataInitializerService {

    private final EmpleadoRepository empleadoRepository;
    private final ProyectoRepository proyectoRepository;
    private final EmpleadoProyectoRepository empleadoProyectoRepository;

    private final Random random = new Random();

    private List<Empleado> empleados;
    private List<Proyecto> proyectos;

    @PostConstruct
    public void init() {

        if (empleadoRepository.count() == 0) {
            empleadoRepository.saveAll(generateEmpleados());
            System.out.println("✅ Empleados insertados");
        }

        if (proyectoRepository.count() == 0) {
            proyectoRepository.saveAll(generateProyectos());
            System.out.println("✅ Proyectos insertados");
        }
    }

    // =========================
    // EMPLEADOS (50)
    // =========================
    private List<Empleado> generateEmpleados() {
        return List.of(

                emp("12345678Z","Juan","Perez","Gomez","600000001","600100001","juan1@email.com",1990,5,12, 2020,1,10, null, "S","S"),
                emp("23456789D","Maria","Lopez","Fernandez","600000002",null,"maria2@email.com",1988,3,22, 2019,2,15, null, "C","S"),
                emp("34567890V","Carlos","Sanchez","Ruiz","600000003","600100003","carlos3@email.com",1992,7,1, 2021,3,20, null, "S","N"),
                emp("45678901G","Ana","Martinez","Diaz","600000004",null,"ana4@email.com",1995,11,30, 2022,4,25, null, "S","S"),
                emp("56789012B","Luis","Garcia","Moreno","600000005","600100005","luis5@email.com",1985,9,10, 2018,6,10, null, "C","N"),

                emp("67890123N","Elena","Torres","Navarro","600000006","600100006","elena6@email.com",1993,2,14, 2020,7,11, null, "S","S"),
                emp("78901234H","Pedro","Ramirez","Santos","600000007",null,"pedro7@email.com",1987,8,19, 2017,8,1, null, "C","S"),
                emp("89012345J","Lucia","Gil","Ortega","600000008","600100008","lucia8@email.com",1996,12,5, 2023,1,1, null, "S","N"),
                emp("90123456S","Jorge","Vazquez","Castro","600000009",null,"jorge9@email.com",1989,4,18, 2016,3,12, null, "C","S"),
                emp("11223344B","Paula","Ramos","Iglesias","600000010","600100010","paula10@email.com",1991,6,23, 2019,9,9, null, "S","S"),

                // 👇 algunos dados de baja (IMPORTANTE)
                emp("22334455C","Diego","Herrera","Molina","600000011",null,"diego11@email.com",1986,1,15, 2015,2,2, 2023, "C","N"),
                emp("33445566K","Sara","Suarez","Delgado","600000012","600100012","sara12@email.com",1994,10,10, 2021,11,11, null, "S","S"),
                emp("44556677E","Alberto","Cruz","Marin","600000013",null,"alberto13@email.com",1983,7,7, 2014,5,5, null, "C","S"),
                emp("55667788T","Carmen","Ortiz","Prieto","600000014","600100014","carmen14@email.com",1997,3,3, 2022,6,6, null, "S","N"),
                emp("66778899R","Ruben","Serrano","Calvo","600000015",null,"ruben15@email.com",1982,12,12, 2013,4,4, 2024, "C","S")

        );
    }

    // =========================
    // PROYECTOS (30)
    // =========================
    private List<Proyecto> generateProyectos() {
        return List.of(
                proy("Desarrollo web corporativo",
                        "Madrid",
                        "Proyecto completo de modernización de la plataforma corporativa con migración a arquitectura microservicios, mejoras de rendimiento, refactorización del frontend y backend y optimización de APIs internas.",
                        2022,1,10, 2023,6,15, null),

                proy("Migración cloud AWS",
                        "Barcelona",
                        "Migración completa de infraestructura on-premise a AWS incluyendo bases de datos, servidores y servicios críticos con alta disponibilidad y estrategias de backup automático.",
                        2023,2,1, 2024,8,30, null),

                proy("App ecommerce móvil",
                        "Valencia",
                        "Desarrollo de aplicación móvil para iOS y Android con sistema de pagos integrado, catálogo dinámico, recomendaciones personalizadas mediante IA y panel administrativo.",
                        2024,3,5, null, null, null, null),

                proy("Sistema ERP interno",
                        "Sevilla",
                        "Implantación de un ERP interno para gestión de empleados, proyectos, recursos y facturación con integración con sistemas legacy existentes en la empresa.",
                        2022,9,1, 2023,12,20, null),

                proy("BI ventas Power BI",
                        "Madrid",
                        "Proyecto de inteligencia de negocio para análisis de ventas, creación de dashboards dinámicos, integración con bases de datos SQL y automatización de informes semanales.",
                        2024,4,1, null, null, null, null),
                pro("Desarrollo web corporativo","Madrid","Frontend/backend"),
                pro("Migración cloud","Barcelona","AWS"),
                pro("App ecommerce","Valencia","Android/iOS"),
                pro("ERP interno","Sevilla","Gestión"),
                pro("BI ventas","Madrid","Power BI"),
                pro("Automatización procesos","Bilbao","RPA"),
                pro("Rediseño web","Zaragoza","UX/UI"),
                pro("CRM Salesforce","Madrid","CRM"),
                pro("Seguridad informática","Valencia","Auditoría"),
                pro("Optimización BD","Barcelona","SQL tuning"),

                pro("E-learning","Madrid","Cursos"),
                pro("Inventario","Sevilla","Stock"),
                pro("Chatbot","Madrid","IA"),
                pro("Pagos online","Valencia","Stripe"),
                pro("Auditoría TI","Bilbao","Seguridad"),
                pro("Portal empleados","Madrid","Intranet"),
                pro("Big Data","Barcelona","Hadoop"),
                pro("Reservas hotel","Mallorca","Booking"),
                pro("App fitness","Madrid","Salud"),
                pro("Documental","Sevilla","Digital"),

                pro("Monitoring","Valencia","Nagios"),
                pro("Facturación","Madrid","ERP"),
                pro("Videojuego","Barcelona","Unity"),
                pro("IA ventas","Madrid","ML"),
                pro("Inmobiliaria","Valencia","Web"),
                pro("Transporte","Bilbao","Movilidad"),
                pro("Tickets soporte","Madrid","Helpdesk"),
                pro("Blockchain","Barcelona","Crypto"),
                pro("Noticias","Sevilla","CMS"),
                pro("RRHH","Madrid","HR system")
        );
    }

    // =========================
    // HELPERS
    // =========================
    private Empleado emp(
            String nif,
            String n,
            String a1,
            String a2,
            String tel1,
            String tel2,
            String email,
            int y, int m, int d,
            int altaY, int altaM, int altaD,
            Integer bajaY,
            String estadoCivil,
            String formacion
    ) {

        Empleado e = new Empleado();
        e.setNif(nif);
        e.setNombre(n);
        e.setApellido1(a1);
        e.setApellido2(a2);
        e.setTelefono1(tel1);
        e.setTelefono2(tel2);
        e.setEmail(email);
        e.setFechaNacimiento(LocalDate.of(y, m, d));
        e.setFechaAlta(LocalDate.of(altaY, altaM, altaD));
        e.setFechaBaja(bajaY != null ? LocalDate.of(bajaY, 1, 1) : null);
        e.setEstadoCivil(estadoCivil);
        e.setFormacionUniversitaria(formacion);

        return e;
    }

    private Proyecto pro(String desc, String lugar, String obs) {
        Proyecto p = new Proyecto();
        p.setDescripcion(desc);
        p.setLugar(lugar);
        p.setObservaciones(obs);
        p.setFechaInicio(LocalDate.of(2024,1,1));
        return p;
    }

    private Proyecto proy(
            String desc,
            String lugar,
            String obs,
            int iY, int iM, int iD,
            Integer fY, Integer fM, Integer fD,
            Integer bajaY
    ) {

        Proyecto p = new Proyecto();
        p.setDescripcion(desc);
        p.setLugar(lugar);
        p.setObservaciones(obs);
        p.setFechaInicio(LocalDate.of(iY, iM, iD));

        if (fY != null) {
            p.setFechaFin(LocalDate.of(fY, fM, fD));
        }

        if (bajaY != null) {
            p.setFechaBaja(LocalDate.of(bajaY, 1, 1));
        }

        p.setImagenUrl("https://picsum.photos/seed/" + desc.hashCode() + "/600/400");

        return p;
    }


    // =========================
    // RELACIONES M:M
    // =========================
    private List<EmpleadoProyecto> generateRelaciones() {

        List<EmpleadoProyecto> list = new ArrayList<>();

        for (Empleado e : empleados) {

            // entre 1 y 3 proyectos por empleado
            int count = 1 + random.nextInt(3);

            Set<Integer> used = new HashSet<>();

            for (int i = 0; i < count; i++) {

                Proyecto p;
                do {
                    p = proyectos.get(random.nextInt(proyectos.size()));
                } while (!used.add(p.getIdProyecto()));

                EmpleadoProyecto ep = new EmpleadoProyecto();
                ep.setEmpleado(e);
                ep.setProyecto(p);
                ep.setFechaAlta(LocalDate.of(2024, random.nextInt(12) + 1, random.nextInt(28) + 1));

                list.add(ep);
            }
        }

        return list;
    }

}
