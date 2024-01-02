# Presentación del Proyecto

Aunque asi no se suelen hacer los README, me parece interesante esta forma de presentar el proyecto.

El proyecto está estructurado en MVVM, para una aplicación mediana me parece el mejor patrón de diseño, más fácil y rápido de implementar que clean architecture además de que al ser una aplicación más enfocada en la UI que en la propia arquitectura, era más adecuado.

Se ha utilizado viewbinding para tener todavía más seguridad de la que ya ofrece Kotlin frente a los nullpoints, así como hacer el código más manejable sin tener que estar pendiente de los scopes de las variables.

En lugar de usar variables LiveData, se ha utilizado un patrón observer, lo que me permitía tener más flexibilidad a la hora de poder hacer mi propia lógica personalizada en la UI, así como facilitar la labor de testing de la propia app.

Para hacer las peticiones a la API, he utilizado la librería Retrofit al ser la más utilizada por la comunidad para este tipo de tareas, así como ser la más cómoda y la que tiene más comunidad y documentación disponible. La conexión al API se ha realizado con un factory para simplificar y reutilizar el máximo código posible sin complicarme mucho. De tener más de una activity o más de una API que necesiten Retrofit, sería interesante implementar inyección de dependencias para que mantengan la instancia.

El resto de librerías son Glide, para mostrar las imágenes desde la URL, Mockito para el testing debido a su gran compatibilidad con JUnit y su versatilidad para crear los test.

Como stack de herramientas, he utilizado Sourcetree como cliente de Git, Android Studio, Postman para las peticiones, el plugin "JSON to Kotlin Class" para convertir el enorme JSON que devuelve la API a data class en Kotlin de manera más manejable y al tener todos los atributos convertidos nos será más fácil crecer en la app así como operar con los datos de cada usuario.

Los cambios que he planteado en el diseño manteniendo su estética minimalista han sido eliminar las flechas de los UserAdapters, puesto que el usuario busca que al pulsar cualquier punto de la app, la app sea reactiva. Por falta de tiempo, no he podido implementar el MapView. Se ha creado una rama con lo que me ha dado tiempo a implementar, pero queda para líneas futuras, igual que colocar bien la imagen en la activity de detalle del usuario, que no se me quedaba del todo bien. Como mejoras también he puesto un aviso para el usuario sobre si está o no conectado a internet, cambio tanto de símbolo de género como del fondo y una animación al hacer scroll.

En cuanto al filtro, la forma de implementarlo respetando todo lo posible la estética minimalista de la aplicación ha sido un EditText que ocultamos y desocultamos tanto con el menú como con los botones que aparecen debajo del EditText. A futuro, sería interesante crear filtros más completos y con más campos.
____________________________________________________________________________________________________________________________________________________________________________________

# Project Presentation
Although READMEs are not usually done, I find this way of presenting the project interesting.

The project is structured in MVVM, for a medium-sized application, I find it the best design pattern, easier and faster to implement than clean architecture, especially since the application is more focused on the UI than on the architecture.

ViewBinding has been used for even more security than Kotlin already offers against null points, as well as making the code more manageable without having to worry about variable scopes.

Instead of using LiveData variables, an observer pattern has been used, allowing more flexibility when making custom logic in the UI and facilitating app testing.

To make API requests, Retrofit library has been used, as it is the most used by the community for this type of task, and it is comfortable with more community and documentation available. The connection to the API has been made with a factory to simplify and reuse the maximum possible code without complicating it too much. If there were more than one activity or more than one API that needed Retrofit, it would be interesting to implement dependency injection to maintain the instance.

The other libraries used are Glide, to display images from the URL, Mockito for testing due to its great compatibility with JUnit and its versatility to create tests.

As a tool stack, I used Sourcetree as a Git client, Android Studio, Postman for requests, the JSON to Kotlin class plugin to convert the huge JSON returned by the API to Kotlin data classes, and having all attributes converted makes it easier for us to grow in the app as well as operate with each user's data.

The changes I proposed in the design, maintaining its minimalist aesthetics, were to remove the arrows from the user adapters since the user expects the app to be reactive when clicking anywhere on the app. Due to lack of time, I could not implement the MapView. I created a branch with what I had time to implement, but it remains for future lines, just like placing the image correctly in the user detail activity, which was not quite right. As improvements, I also added a notice to the user about whether or not they are connected to the internet, changing both the gender symbol and the background, and an animation when scrolling.

Regarding the filter, the way to implement it while respecting the minimalist aesthetics of the application has been an EditText that we hide and show both with the menu and with the buttons that appear below the EditText. In the future, it would be interesting to create more complete filters with more fields.
