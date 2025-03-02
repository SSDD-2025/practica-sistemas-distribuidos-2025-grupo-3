package com.example.demo.Data;

import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.CommunityRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Comment;
import com.example.demo.model.Community;
import com.example.demo.model.Post;
import com.example.demo.model.User;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    
    @Autowired
    private CommunityRepository communityRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    @PostConstruct
    public void run() throws Exception{

        ClassPathResource imgFile;

        /* COMMUNITIES */
        communityRepository.save(new Community("Java"));
        communityRepository.save(new Community("Futbol"));
        communityRepository.save(new Community("Tecnología"));
        communityRepository.save(new Community("Viajes"));
        communityRepository.save(new Community("Cocina"));
        communityRepository.save(new Community("Libros"));
        communityRepository.save(new Community("Fotografía"));

        /* USERS */
            /* Guest user */
        imgFile = new ClassPathResource("static/assets/img/default-user-profile-image.webp");
        userRepository.save(new User("Invitado", "-", "-", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
            /* Users */
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview1.webp");
        userRepository.save(new User("Juan Pérez", "password1", "juan.perez@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
        imgFile = new ClassPathResource("static/assets/img/default-user-profile-image.webp");
        userRepository.save(new User("María García", "password2", "maria.garcia@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview2.webp");
        userRepository.save(new User("Carlos Rodríguez", "password3", "carlos.rodriguez@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview3.webp");
        userRepository.save(new User("Ana Martínez", "password4", "ana.martinez@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview4.webp");
        userRepository.save(new User("Luis Hernández", "password5", "luis.hernandez@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
        imgFile = new ClassPathResource("static/assets/img/default-user-profile-image.webp");
        userRepository.save(new User("Laura López", "password6", "laura.lopez@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview5.webp");
        userRepository.save(new User("José González", "password7", "jose.gonzalez@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
        imgFile = new ClassPathResource("static/assets/img/default-user-profile-image.webp");
        userRepository.save(new User("Marta Sánchez", "password8", "marta.sanchez@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview6.webp");
        userRepository.save(new User("David Fernández", "password9", "david.fernandez@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview7.webp");
        userRepository.save(new User("Elena Ruiz", "password10", "elena.ruiz@example.com", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));
            /* Developer users */
        imgFile = new ClassPathResource("static/assets/img/imagensergio.gif");
        userRepository.save(new User("Sergio", "454548", "s.espinosa.2020@alumnos.urjc.es", new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath())));

        User user1 = userRepository.findByEmail("juan.perez@example.com");
        User user2 = userRepository.findByEmail("maria.garcia@example.com");
        User user3 = userRepository.findByEmail("carlos.rodriguez@example.com");
        User user4 = userRepository.findByEmail("ana.martinez@example.com");

        /* POSTS */
        Community javaCommunity = communityRepository.findByName("Java");
        postRepository.save(new Post("Introducción a Java", "Java es un lenguaje de programación de propósito general que es concurrente, basado en clases y orientado a objetos. Fue diseñado para tener la menor cantidad de dependencias de implementación posible.", null, null, user1, javaCommunity));
        postRepository.save(new Post("Java y la Programación Orientada a Objetos", "La programación orientada a objetos (POO) es un paradigma de programación basado en el concepto de 'objetos', que pueden contener datos y código: datos en forma de campos, y código, en forma de procedimientos.", null, null, user2, javaCommunity));
        postRepository.save(new Post("Manejo de Excepciones en Java", "El manejo de excepciones en Java es una poderosa herramienta que permite a los desarrolladores manejar errores y otras condiciones excepcionales de manera controlada y predecible.", null, null, user3, javaCommunity));
        postRepository.save(new Post("Colecciones en Java", "Las colecciones en Java son marcos que proporcionan una arquitectura para almacenar y manipular el grupo de objetos. Todas las operaciones que se realizan en una estructura de datos, como búsqueda, ordenación, inserción, manipulación, eliminación, etc., se pueden realizar mediante Java Collections.", null, null, user4, javaCommunity));
        postRepository.save(new Post("Streams en Java", "Los Streams en Java proporcionan una forma moderna y funcional de procesar colecciones de datos. Permiten realizar operaciones como filtrado, mapeo, reducción y más de manera concisa y eficiente.", null, null, user1, javaCommunity));
        postRepository.save(new Post("Programación Concurrente en Java", "La programación concurrente en Java permite la ejecución simultánea de múltiples hilos de ejecución. Esto es útil para aprovechar al máximo los recursos del sistema y mejorar el rendimiento de las aplicaciones.", null, null, user2, javaCommunity));
        postRepository.save(new Post("Introducción a Spring Framework", "Spring es un marco de trabajo para el desarrollo de aplicaciones empresariales en Java. Proporciona soporte integral para el desarrollo de aplicaciones Java modernas y eficientes.", null, null, user3, javaCommunity));
        postRepository.save(new Post("Hibernate y JPA en Java", "Hibernate es una herramienta de mapeo objeto-relacional (ORM) para Java. JPA (Java Persistence API) es una especificación de Java que describe una interfaz común para el mapeo de objetos a bases de datos relacionales.", null, null, user4, javaCommunity));
        postRepository.save(new Post("Desarrollo de Aplicaciones Web con Java", "Java es una excelente opción para el desarrollo de aplicaciones web. Con frameworks como Spring y herramientas como JSP y Servlets, es posible crear aplicaciones web robustas y escalables.", null, null, user1, javaCommunity));
        postRepository.save(new Post("Buenas Prácticas en Java", "Las buenas prácticas en Java incluyen el uso de convenciones de nomenclatura, la escritura de código limpio y legible, la realización de pruebas unitarias, y el uso de patrones de diseño cuando sea apropiado.", null, null, user2, javaCommunity));

        Community futbolCommunity = communityRepository.findByName("Futbol");
        postRepository.save(new Post("Historia del Fútbol", "El fútbol es un deporte de equipo jugado entre dos conjuntos de once jugadores cada uno y algunos árbitros que se ocupan de que las normas se cumplan correctamente. Es ampliamente considerado el deporte más popular del mundo.", null, null, user1, futbolCommunity));
        postRepository.save(new Post("Reglas Básicas del Fútbol", "El fútbol se juega con una pelota esférica entre dos equipos de once jugadores cada uno. El objetivo del juego es marcar más goles que el oponente en el tiempo reglamentario.", null, null, user2, futbolCommunity));
        postRepository.save(new Post("Las Mejores Ligas de Fútbol", "Las ligas de fútbol más importantes del mundo incluyen la Premier League, La Liga, la Serie A, la Bundesliga y la Ligue 1. Estas ligas son conocidas por su alta calidad de juego y sus equipos competitivos.", null, null, user3, futbolCommunity));
        postRepository.save(new Post("Los Mejores Jugadores de Fútbol de Todos los Tiempos", "Algunos de los mejores jugadores de fútbol de todos los tiempos incluyen a Pelé, Diego Maradona, Lionel Messi y Cristiano Ronaldo. Estos jugadores han dejado una marca indeleble en la historia del fútbol.", null, null, user4, futbolCommunity));
        postRepository.save(new Post("Tácticas y Estrategias en el Fútbol", "Las tácticas y estrategias en el fútbol son esenciales para el éxito de un equipo. Incluyen formaciones, estilos de juego y planes de juego específicos para diferentes situaciones.", null, null, user1, futbolCommunity));
        postRepository.save(new Post("El Fútbol Femenino", "El fútbol femenino ha crecido en popularidad y calidad en los últimos años. Las competiciones como la Copa Mundial Femenina y las ligas nacionales han ayudado a elevar el perfil del deporte.", null, null, user2, futbolCommunity));
        postRepository.save(new Post("La Importancia del Entrenamiento en el Fútbol", "El entrenamiento en el fútbol es crucial para el desarrollo de habilidades, la condición física y la cohesión del equipo. Los entrenadores juegan un papel vital en la preparación de los jugadores para los partidos.", null, null, user3, futbolCommunity));
        postRepository.save(new Post("Grandes Momentos en la Historia del Fútbol", "La historia del fútbol está llena de grandes momentos, desde goles icónicos hasta partidos inolvidables. Estos momentos han definido el deporte y han emocionado a los aficionados de todo el mundo.", null, null, user4, futbolCommunity));
        postRepository.save(new Post("El Impacto del Fútbol en la Cultura Global", "El fútbol tiene un impacto significativo en la cultura global. Es un deporte que une a personas de diferentes orígenes y culturas, y tiene el poder de inspirar y emocionar a millones de personas.", null, null, user1, futbolCommunity));
        postRepository.save(new Post("El Futuro del Fútbol", "El futuro del fútbol parece brillante, con nuevas tecnologías, tácticas innovadoras y una creciente popularidad en todo el mundo. El deporte continuará evolucionando y emocionando a los aficionados en los años venideros.", null, null, user2, futbolCommunity));

        Community tecnologiaCommunity = communityRepository.findByName("Tecnología");
        postRepository.save(new Post("El Futuro de la Inteligencia Artificial", "La inteligencia artificial (IA) está transformando el mundo a un ritmo acelerado. Desde asistentes virtuales hasta vehículos autónomos, la IA está cambiando la forma en que vivimos y trabajamos.", null, null, user1, tecnologiaCommunity));
        postRepository.save(new Post("Blockchain y su Impacto en la Industria", "Blockchain es una tecnología revolucionaria que está cambiando la forma en que se realizan las transacciones y se almacenan los datos. Su impacto se siente en diversas industrias, desde las finanzas hasta la salud.", null, null, user2, tecnologiaCommunity));
        postRepository.save(new Post("El Auge de la Computación Cuántica", "La computación cuántica promete resolver problemas que son intratables para las computadoras clásicas. Esta tecnología emergente tiene el potencial de revolucionar campos como la criptografía y la simulación de materiales.", null, null, user3, tecnologiaCommunity));
        postRepository.save(new Post("La Revolución del Internet de las Cosas (IoT)", "El Internet de las Cosas (IoT) está conectando dispositivos de todo tipo a la red, permitiendo una mayor automatización y control. Desde hogares inteligentes hasta ciudades conectadas, el IoT está cambiando nuestra forma de interactuar con el mundo.", null, null, user4, tecnologiaCommunity));
        postRepository.save(new Post("Realidad Virtual y Aumentada: El Futuro del Entretenimiento", "La realidad virtual (VR) y la realidad aumentada (AR) están transformando la industria del entretenimiento. Estas tecnologías ofrecen experiencias inmersivas que están cambiando la forma en que jugamos, aprendemos y trabajamos.", null, null, user1, tecnologiaCommunity));
        postRepository.save(new Post("Ciberseguridad en la Era Digital", "La ciberseguridad es una preocupación creciente en un mundo cada vez más digital. Proteger nuestros datos y sistemas de los ciberataques es crucial para mantener la confianza en la tecnología.", null, null, user2, tecnologiaCommunity));
        postRepository.save(new Post("El Impacto de la 5G en la Conectividad Global", "La tecnología 5G está llevando la conectividad a un nuevo nivel, ofreciendo velocidades más rápidas y una menor latencia. Esto permitirá nuevas aplicaciones y servicios que antes eran imposibles.", null, null, user3, tecnologiaCommunity));
        postRepository.save(new Post("El Papel de la Robótica en el Futuro del Trabajo", "La robótica está cambiando la forma en que trabajamos, desde la automatización de tareas repetitivas hasta la colaboración con robots en el lugar de trabajo. Esta tecnología tiene el potencial de aumentar la eficiencia y la productividad.", null, null, user4, tecnologiaCommunity));
        postRepository.save(new Post("Big Data y Análisis de Datos: Transformando la Toma de Decisiones", "El big data y el análisis de datos están transformando la forma en que las empresas toman decisiones. Al analizar grandes volúmenes de datos, las organizaciones pueden obtener insights valiosos y tomar decisiones más informadas.", null, null, user1, tecnologiaCommunity));
        postRepository.save(new Post("El Futuro de la Computación en la Nube", "La computación en la nube está cambiando la forma en que almacenamos y procesamos los datos. Esta tecnología ofrece flexibilidad, escalabilidad y ahorro de costos, y está impulsando la innovación en diversas industrias.", null, null, user2, tecnologiaCommunity));

        Community viajesCommunity = communityRepository.findByName("Viajes");
        postRepository.save(new Post("Los Mejores Destinos para Viajar en 2025", "Descubre los destinos más populares y emocionantes para viajar en 2025. Desde playas paradisíacas hasta ciudades vibrantes, hay algo para todos.", null, null, user1, viajesCommunity));
        postRepository.save(new Post("Consejos para Viajar con un Presupuesto Limitado", "Viajar no tiene que ser caro. Aquí tienes algunos consejos para ahorrar dinero y disfrutar de tus viajes sin gastar una fortuna.", null, null, user2, viajesCommunity));
        postRepository.save(new Post("Las Mejores Experiencias de Aventura en el Mundo", "Si eres un amante de la aventura, estos destinos te ofrecerán experiencias inolvidables, desde el senderismo en montañas hasta el buceo en arrecifes de coral.", null, null, user3, viajesCommunity));
        postRepository.save(new Post("Cómo Planificar un Viaje Perfecto", "Planificar un viaje puede ser abrumador, pero con estos consejos, podrás organizar todo de manera eficiente y disfrutar de unas vacaciones sin estrés.", null, null, user4, viajesCommunity));
        postRepository.save(new Post("Los Mejores Lugares para Hacer Turismo Gastronómico", "Descubre los destinos donde la comida es la protagonista. Desde mercados locales hasta restaurantes con estrellas Michelin, estos lugares te harán agua la boca.", null, null, user1, viajesCommunity));
        postRepository.save(new Post("Viajes Sostenibles: Cómo Reducir tu Huella de Carbono", "Viajar de manera sostenible es posible. Aquí tienes algunos consejos para reducir tu impacto ambiental mientras exploras el mundo.", null, null, user2, viajesCommunity));
        postRepository.save(new Post("Las Mejores Playas del Mundo", "Si eres un amante del sol y la arena, no te pierdas estas playas impresionantes que te dejarán sin aliento.", null, null, user3, viajesCommunity));

        Community cocinaCommunity = communityRepository.findByName("Cocina");
        postRepository.save(new Post("Recetas Fáciles para Principiantes", "Si estás empezando en la cocina, estas recetas fáciles y deliciosas te ayudarán a ganar confianza y habilidades.", null, null, user4, cocinaCommunity));
        postRepository.save(new Post("Los Mejores Platos de la Cocina Italiana", "La cocina italiana es famosa en todo el mundo. Descubre cómo preparar algunos de los platos más icónicos de Italia.", null, null, user1, cocinaCommunity));
        postRepository.save(new Post("Consejos para Cocinar de Manera Saludable", "Cocinar de manera saludable no tiene que ser complicado. Aquí tienes algunos consejos para preparar comidas nutritivas y deliciosas.", null, null, user2, cocinaCommunity));
        postRepository.save(new Post("Explorando la Cocina Asiática", "La cocina asiática es diversa y llena de sabores únicos. Aprende a preparar algunos de los platos más populares de Asia.", null, null, user3, cocinaCommunity));
        postRepository.save(new Post("Postres Irresistibles para Endulzar tu Día", "Si tienes un antojo de algo dulce, estas recetas de postres te encantarán. Desde pasteles hasta galletas, hay algo para todos.", null, null, user4, cocinaCommunity));

        /* COMMENTS */
        commentRepository.save(new Comment("Muy interesante tu post", userRepository.findById(6L).get(), postRepository.findById(1L).get()));
        commentRepository.save(new Comment("Gracias por compartir", userRepository.findById(2L).get(), postRepository.findById(1L).get()));
        
    }
}
