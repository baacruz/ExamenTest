# PM2ExamenHerramientas-97y42
Examen Test – Gestor de Asignación de Herramientas 
ExamenTest es una aplicación Android desarrollada en Kotlin utilizando Jetpack 
Compose, cuyo objetivo es facilitar la gestión y asignación de herramientas a técnicos. 
La aplicación permite registrar tanto herramientas como técnicos y asignarlos por un 
período de tiempo determinado, controlando en todo momento la disponibilidad de cada 
herramienta. 
Capturas de Pantalla 
Nota: Las capturas deben generarse directamente desde la aplicación y 
agregarse al repositorio dentro de una carpeta, por ejemplo: /screenshots. 
Registro de Herramienta Registro de Técnico  
Asignación 
• Figura 1. Pantalla para dar de alta nuevas herramientas.  
• Figura 2. Pantalla para registrar técnicos en el sistema.  
• Figura 3. Pantalla principal para asignar una herramienta disponible a un 
técnico. 
Funcionalidades Principales 
Registro de Técnicos: 
Permite agregar nuevos técnicos al sistema ingresando su nombre, número de 
teléfono y especialidad. 
Registro de Herramientas: 
Facilita el registro de herramientas incluyendo nombre, descripción, 
especificaciones y una fotografía. 
Asignación de Herramientas: 
Permite asignar una herramienta disponible a un técnico, seleccionando una 
fecha de inicio y una fecha de finalización. Al realizar la asignación, el estado de la 
herramienta se actualiza automáticamente a “ASIGNADA”. 
Interfaz Moderna: 
La aplicación está desarrollada completamente con Jetpack Compose, 
ofreciendo una interfaz limpia, moderna y reactiva. 
Persistencia Local: 
Toda la información se almacena en una base de datos SQLite local, 
asegurando que los registros se mantengan incluso al cerrar la aplicación. 
Pasos para Pruebas 
Actualmente, el proyecto no cuenta con pruebas unitarias ni pruebas de 
instrumentación automatizadas. Sin embargo, se pueden realizar pruebas manuales 
siguiendo los pasos que se describen a continuación: 
1. Ejecutar la Aplicación 
Conecta un dispositivo Android con Android 7.0 o superior, o bien inicia un 
emulador de Android. 
Presiona Shift + F10 o haz clic en el botón Run 'app' dentro de Android Studio. 
2. Probar los Flujos de Usuario 
Registrar un Técnico 
Navega a la pantalla “Registrar Técnico”. 
Completa los campos de nombre, teléfono y especialidad. 
Presiona el botón “Agregar”. 
Verifica que se muestre un mensaje de confirmación (Toast). 
Registrar una Herramienta 
Accede a la pantalla “Registrar Herramienta”. 
Añade una fotografía, el nombre, la descripción y las especificaciones de la 
herramienta. 
Presiona “Agregar”. 
Confirma que se muestre un mensaje indicando que el registro fue exitoso. 
Asignar una Herramienta 
Dirígete a la pantalla “Asignar Herramienta”. 
Utiliza los menús desplegables para seleccionar una herramienta y un técnico 
previamente registrados. 
Selecciona la fecha de inicio y la fecha de finalización usando los selectores de 
fecha. 
Haz clic en “Asignar” y luego en “Confirmar” dentro del diálogo emergente. 
Verifica que aparezca el mensaje de éxito y que los campos se limpien para 
realizar una nueva asignación.
