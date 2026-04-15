package pedro.proyectofinal.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pedro.proyectofinal.model.Empleado;
import pedro.proyectofinal.model.Proyecto;
import pedro.proyectofinal.repository.EmpleadoRepository;
import pedro.proyectofinal.repository.ProyectoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataInitializerService {

    private final EmpleadoRepository empleadoRepository;
    private final ProyectoRepository proyectoRepository;

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

                emp("12345678Z","Juan","Perez","Gomez","600000001","juan1@email.com",1990,5,12),
                emp("23456789D","Maria","Lopez","Fernandez","600000002","maria2@email.com",1988,3,22),
                emp("34567890V","Carlos","Sanchez","Ruiz","600000003","carlos3@email.com",1992,7,1),
                emp("45678901G","Ana","Martinez","Diaz","600000004","ana4@email.com",1995,11,30),
                emp("56789012B","Luis","Garcia","Moreno","600000005","luis5@email.com",1985,9,10),
                emp("67890123N","Elena","Torres","Navarro","600000006","elena6@email.com",1993,2,14),
                emp("78901234H","Pedro","Ramirez","Santos","600000007","pedro7@email.com",1987,8,19),
                emp("89012345J","Lucia","Gil","Ortega","600000008","lucia8@email.com",1996,12,5),
                emp("90123456S","Jorge","Vazquez","Castro","600000009","jorge9@email.com",1989,4,18),
                emp("11223344B","Paula","Ramos","Iglesias","600000010","paula10@email.com",1991,6,23),

                emp("22334455C","Diego","Herrera","Molina","600000011","diego11@email.com",1986,1,15),
                emp("33445566K","Sara","Suarez","Delgado","600000012","sara12@email.com",1994,10,10),
                emp("44556677E","Alberto","Cruz","Marin","600000013","alberto13@email.com",1983,7,7),
                emp("55667788T","Carmen","Ortiz","Prieto","600000014","carmen14@email.com",1997,3,3),
                emp("66778899R","Ruben","Serrano","Calvo","600000015","ruben15@email.com",1982,12,12),
                emp("77889900W","Marta","Blanco","Reyes","600000016","marta16@email.com",1998,8,8),
                emp("88990011A","Ivan","Castillo","Cano","600000017","ivan17@email.com",1990,9,9),
                emp("99001122Q","Laura","Campos","Vidal","600000018","laura18@email.com",1992,11,11),
                emp("10111213L","Adrian","Leon","Pascual","600000019","adrian19@email.com",1984,6,6),
                emp("12131415X","Noelia","Mendez","Roca","600000020","noelia20@email.com",1993,5,5),

                emp("13141516Y","Victor","Soto","Nieto","600000021","victor21@email.com",1981,2,2),
                emp("14151617Z","Cristina","Parra","Bravo","600000022","cristina22@email.com",1999,1,1),
                emp("15161718M","Raul","Ibañez","Fuentes","600000023","raul23@email.com",1987,3,3),
                emp("16171819F","Beatriz","Cortes","Aguilar","600000024","beatriz24@email.com",1996,4,4),
                emp("17181920P","Hugo","Peña","Soler","600000025","hugo25@email.com",1985,5,5),
                emp("18192021D","Natalia","Ferrer","Rios","600000026","natalia26@email.com",1991,6,6),
                emp("19202122G","Oscar","Dominguez","Crespo","600000027","oscar27@email.com",1988,7,7),
                emp("20212223H","Silvia","Vera","Pastor","600000028","silvia28@email.com",1995,8,8),
                emp("21222324J","Pablo","Roldan","Sanz","600000029","pablo29@email.com",1990,9,9),
                emp("22232425U","Irene","Luna","Bravo","600000030","irene30@email.com",1993,10,10),

                emp("23242526V","Sergio","Mora","Sierra","600000031","sergio31@email.com",1986,11,11),
                emp("24252627N","Alicia","Rey","Pardo","600000032","alicia32@email.com",1997,12,12),
                emp("25262728B","Fernando","Cano","Duran","600000033","fernando33@email.com",1984,1,1),
                emp("26272829Z","Patricia","Sanz","Gallego","600000034","patricia34@email.com",1992,2,2),
                emp("27282930S","Manuel","Nieto","Benitez","600000035","manuel35@email.com",1989,3,3),
                emp("28293031Q","Rocio","Calderon","Paz","600000036","rocio36@email.com",1994,4,4),
                emp("29303132W","Daniel","Rios","Campos","600000037","daniel37@email.com",1983,5,5),
                emp("30313233E","Teresa","Guerrero","Vega","600000038","teresa38@email.com",1996,6,6),
                emp("31323334R","Andres","Fuentes","Ramos","600000039","andres39@email.com",1987,7,7),
                emp("32333435T","Claudia","Prieto","Gil","600000040","claudia40@email.com",1998,8,8),

                emp("33343536Y","Mario","Santos","Moya","600000041","mario41@email.com",1991,9,9),
                emp("34353637U","Eva","Ortega","Leon","600000042","eva42@email.com",1995,10,10),
                emp("35363738I","Javier","Navarro","Rivas","600000043","javier43@email.com",1982,11,11),
                emp("36373839O","Monica","Vidal","Cabrera","600000044","monica44@email.com",1993,12,12),
                emp("37383940P","Alex","Morales","Segura","600000045","alex45@email.com",1986,1,1)
        );
    }

    // =========================
    // PROYECTOS (30)
    // =========================
    private List<Proyecto> generateProyectos() {
        return List.of(
                pro("Desarrollo web corporativo","Madrid","Frontend y backend"),
                pro("Migración a la nube","Barcelona","AWS y Azure"),
                pro("App móvil ecommerce","Valencia","Android e iOS"),
                pro("Sistema interno ERP","Sevilla","Gestión interna"),
                pro("Análisis de datos ventas","Madrid","Power BI"),
                pro("Automatización procesos","Bilbao","RPA"),
                pro("Rediseño web","Zaragoza","UX/UI"),
                pro("Implementación CRM","Madrid","Salesforce"),
                pro("Seguridad informática","Valencia","Auditoría"),
                pro("Optimización BD","Barcelona","Performance"),

                pro("Plataforma e-learning","Madrid","Cursos online"),
                pro("Sistema inventario","Sevilla","Stock"),
                pro("Chatbot atención cliente","Madrid","IA"),
                pro("Integración pagos","Valencia","Stripe"),
                pro("Auditoría TI","Bilbao","Seguridad"),
                pro("Portal empleados","Madrid","Intranet"),
                pro("Big Data","Barcelona","Hadoop"),
                pro("Sistema reservas","Mallorca","Hoteles"),
                pro("App fitness","Madrid","Salud"),
                pro("Gestión documental","Sevilla","Digitalización"),

                pro("Monitorización servidores","Valencia","Nagios"),
                pro("Sistema facturación","Madrid","ERP"),
                pro("Videojuego Unity","Barcelona","Game dev"),
                pro("IA predicción ventas","Madrid","ML"),
                pro("Web inmobiliaria","Valencia","Portal"),
                pro("App transporte","Bilbao","Movilidad"),
                pro("Sistema tickets","Madrid","Helpdesk"),
                pro("Blockchain","Barcelona","Criptografía"),
                pro("Portal noticias","Sevilla","CMS"),
                pro("Sistema RRHH","Madrid","Gestión personal")
        );
    }

    // =========================
    // HELPERS
    // =========================
    private Empleado emp(String nif, String n, String a1, String a2,
                         String tel, String email,
                         int y, int m, int d) {

        Empleado e = new Empleado();
        e.setNif(nif);
        e.setNombre(n);
        e.setApellido1(a1);
        e.setApellido2(a2);
        e.setTelefono1(tel);
        e.setEmail(email);
        e.setFechaNacimiento(LocalDate.of(y, m, d));
        e.setFechaAlta(LocalDate.of(2020,1,1));
        e.setEstadoCivil("S");
        e.setFormacionUniversitaria("S");
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
}