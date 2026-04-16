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
                emp("77889900L","Marta","Dominguez","Rey","600000016","600100016","marta16@email.com",1990,1,5, 2018,2,3, null, "S","S"),
                emp("88990011P","Victor","Leon","Cano","600000017",null,"victor17@email.com",1984,9,21, 2016,5,14, null, "C","N"),
                emp("99001122A","Raquel","Mendez","Vega","600000018","600100018","raquel18@email.com",1993,4,11, 2020,7,7, null, "S","S"),
                emp("10111213Q","Oscar","Nieto","Campos","600000019",null,"oscar19@email.com",1988,2,2, 2017,9,9, null, "C","S"),
                emp("12131415W","Irene","Bravo","Fuentes","600000020","600100020","irene20@email.com",1995,6,30, 2022,8,8, null, "S","N"),

                emp("13141516X","Adrian","Rojas","Peña","600000021",null,"adrian21@email.com",1992,11,9, 2019,1,1, null, "S","S"),
                emp("14151617Y","Noelia","Pardo","Rubio","600000022","600100022","noelia22@email.com",1996,5,18, 2023,2,2, null, "S","S"),
                emp("15161718Z","Hector","Blanco","Lara","600000023",null,"hector23@email.com",1987,7,25, 2015,6,6, null, "C","N"),
                emp("16171819A","Silvia","Cortes","Soto","600000024","600100024","silvia24@email.com",1994,3,12, 2021,10,10, null, "S","S"),
                emp("17181920B","Ivan","Sanz","Pastor","600000025",null,"ivan25@email.com",1989,8,8, 2018,12,12, null, "C","S"),

                emp("18192021C","Patricia","Benitez","Vidal","600000026","600100026","patricia26@email.com",1991,1,19, 2020,4,4, null, "S","N"),
                emp("19202122D","Fernando","Rico","Pozo","600000027",null,"fernando27@email.com",1983,10,14, 2014,3,3, null, "C","S"),
                emp("20212223E","Natalia","Guerrero","Acosta","600000028","600100028","natalia28@email.com",1997,12,1, 2022,5,5, null, "S","S"),
                emp("21222324F","Sergio","Cabrera","Rios","600000029",null,"sergio29@email.com",1990,6,6, 2019,7,7, null, "S","S"),
                emp("22232425G","Beatriz","Lorenzo","Navas","600000030","600100030","beatriz30@email.com",1986,9,27, 2016,8,8, null, "C","N"),

                emp("23242526H","Guillermo","Mora","Salas","600000031",null,"guillermo31@email.com",1982,4,4, 2013,2,2, 2022, "C","S"),
                emp("24252627J","Claudia","Estevez","Roldan","600000032","600100032","claudia32@email.com",1993,7,17, 2020,6,6, null, "S","S"),
                emp("25262728K","Alvaro","Soler","Duran","600000033",null,"alvaro33@email.com",1988,5,5, 2017,11,11, null, "C","N"),
                emp("26272829L","Monica","Tomas","Rivas","600000034","600100034","monica34@email.com",1995,2,20, 2022,9,9, null, "S","S"),
                emp("27282930M","Pablo","Ballesteros","Sierra","600000035",null,"pablo35@email.com",1991,11,11, 2018,3,3, null, "S","S"),

                emp("28293031N","Cristina","Carrasco","Roman","600000036","600100036","cristina36@email.com",1996,1,1, 2023,1,10, null, "S","N"),
                emp("29303132P","Daniel","Parra","Crespo","600000037",null,"daniel37@email.com",1987,8,13, 2015,4,4, null, "C","S"),
                emp("30313233Q","Eva","Aguilar","Beltran","600000038","600100038","eva38@email.com",1994,6,15, 2021,5,5, null, "S","S"),
                emp("31323334R","Mario","Villar","Lago","600000039",null,"mario39@email.com",1985,3,9, 2014,7,7, null, "C","N"),
                emp("32333435S","Lorena","Moya","Cuesta","600000040","600100040","lorena40@email.com",1992,12,28, 2019,8,8, null, "S","S"),

                emp("33343536T","Andres","Valle","Correa","600000041",null,"andres41@email.com",1989,10,10, 2016,9,9, null, "C","S"),
                emp("34353637U","Julia","Segura","Bravo","600000042","600100042","julia42@email.com",1997,4,6, 2022,10,10, null, "S","S"),
                emp("35363738V","Ricardo","Padilla","Ponce","600000043",null,"ricardo43@email.com",1984,2,14, 2013,1,1, 2024, "C","N"),
                emp("36373839W","Sonia","Gallego","Barrios","600000044","600100044","sonia44@email.com",1993,7,7, 2020,2,2, null, "S","S"),
                emp("37383940X","Javier","Ordoñez","Tejada","600000045",null,"javier45@email.com",1988,9,3, 2017,6,6, null, "C","S"),

                emp("38394041Y","Rocio","Velasco","Matos","600000046","600100046","rocio46@email.com",1995,5,25, 2022,7,7, null, "S","N"),
                emp("39404142Z","Tomas","Santana","Quesada","600000047",null,"tomas47@email.com",1986,11,11, 2015,3,3, null, "C","S"),
                emp("40414243A","Veronica","Ibañez","Maldonado","600000048","600100048","veronica48@email.com",1991,8,18, 2019,4,4, null, "S","S"),
                emp("41424344B","Emilio","Redondo","Arce","600000049",null,"emilio49@email.com",1983,6,6, 2012,5,5, 2021, "C","N"),
                emp("42434445C","Nuria","Escobar","Ferrer","600000050","600100050","nuria50@email.com",1996,3,3, 2023,6,6, null, "S","S"),

                emp("43444546D","Roberto","Pascual","Montes","600000051",null,"roberto51@email.com",1987,2,8, 2016,4,4, null, "C","S"),
                emp("44454647E","Alicia","Caballero","Pineda","600000052","600100052","alicia52@email.com",1994,9,17, 2021,5,5, null, "S","S"),
                emp("45464748F","Miguel","Nieto","Saez","600000053",null,"miguel53@email.com",1985,6,1, 2015,7,7, null, "C","N"),
                emp("46474849G","Laura","Vega","Campos","600000054","600100054","laura54@email.com",1993,12,12, 2020,8,8, null, "S","S"),
                emp("47484950H","Raul","Hidalgo","Carmona","600000055",null,"raul55@email.com",1989,4,19, 2018,9,9, null, "S","S"),

                emp("48495051J","Cristina","Vera","Luque","600000056","600100056","cristina56@email.com",1996,1,14, 2022,10,10, null, "S","N"),
                emp("49505152K","Antonio","Reyes","Cortes","600000057",null,"antonio57@email.com",1982,10,30, 2013,11,11, null, "C","S"),
                emp("50515253L","Miriam","Castillo","Paz","600000058","600100058","miriam58@email.com",1995,7,7, 2022,12,12, null, "S","S"),
                emp("51525354M","Julio","Morales","Garrido","600000059",null,"julio59@email.com",1986,5,21, 2016,1,1, null, "C","N"),
                emp("52535455N","Teresa","Prieto","Oliva","600000060","600100060","teresa60@email.com",1992,3,3, 2019,2,2, null, "S","S"),

                emp("53545556P","Marcos","Saavedra","Arroyo","600000061",null,"marcos61@email.com",1990,11,11, 2018,3,3, null, "S","S"),
                emp("54555657Q","Eva","Roldan","Cuellar","600000062","600100062","eva62@email.com",1997,6,18, 2023,4,4, null, "S","N"),
                emp("55565758R","Diego","Benito","Polo","600000063",null,"diego63@email.com",1984,9,9, 2014,5,5, null, "C","S"),
                emp("56575859S","Andrea","Soto","Cardenas","600000064","600100064","andrea64@email.com",1993,8,24, 2020,6,6, null, "S","S"),
                emp("57585960T","Joaquin","Roman","Nieto","600000065",null,"joaquin65@email.com",1988,12,5, 2017,7,7, null, "C","S"),

                emp("58596061U","Natalia","Cuevas","Benavente","600000066","600100066","natalia66@email.com",1991,1,1, 2019,8,8, 2024, "S","N"),
                emp("59606162V","Hugo","Martos","Bautista","600000067",null,"hugo67@email.com",1983,3,3, 2012,9,9, null, "C","S"),
                emp("60616263W","Paola","Serrat","Montero","600000068","600100068","paola68@email.com",1996,4,27, 2022,10,10, null, "S","S"),
                emp("61626364X","Rafael","Pacheco","Gomez","600000069",null,"rafael69@email.com",1987,2,2, 2016,11,11, null, "C","N"),
                emp("62636465Y","Bea","Maldonado","Sola","600000070","600100070","bea70@email.com",1994,5,15, 2021,12,12, null, "S","S"),

                emp("63646566Z","Enrique","Pons","Trujillo","600000071",null,"enrique71@email.com",1985,10,10, 2015,1,1, null, "C","S"),
                emp("64656667A","Clara","Bermudez","Pastor","600000072","600100072","clara72@email.com",1992,9,9, 2019,2,2, null, "S","S"),
                emp("65666768B","Alex","Santiago","Iglesias","600000073",null,"alex73@email.com",1990,7,7, 2018,3,3, null, "S","N"),
                emp("66676869C","Rosa","Calderon","Rico","600000074","600100074","rosa74@email.com",1995,6,6, 2022,4,4, null, "S","S"),
                emp("67686970D","Felipe","Naranjo","Vidal","600000075",null,"felipe75@email.com",1986,11,11, 2016,5,5, null, "C","S"),

                emp("68697071E","Lidia","Montoya","Porras","600000076","600100076","lidia76@email.com",1993,1,23, 2020,6,6, null, "S","S"),
                emp("69707172F","Bruno","Camacho","Del Rio","600000077",null,"bruno77@email.com",1988,4,4, 2017,7,7, null, "C","N"),
                emp("70717273G","Sofia","Otero","Pizarro","600000078","600100078","sofia78@email.com",1997,8,8, 2023,8,8, null, "S","S"),
                emp("71727374H","David","Navarro","Requena","600000079",null,"david79@email.com",1984,12,12, 2014,9,9, null, "C","S"),
                emp("72737475J","Elisa","Aranda","Moya","600000080","600100080","elisa80@email.com",1991,3,3, 2019,10,10, null, "S","S")
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
