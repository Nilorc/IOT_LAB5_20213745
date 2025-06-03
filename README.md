# LAB5_20213745 - Mis Hábitos (App Android)

### Curso: Servicios y Aplicaciones para IoT [1TEL05]  
### Semestre: 2025-1  
### Estudiante: Nilo Rikel Cori Ramos
### Código PUCP: 20213745  
### Fecha de entrega: 31/05/2025

---

## 📱 Descripción del Proyecto

**Mis Hábitos** es una aplicación móvil desarrollada en **Java** con **Android Studio**, que permite a los usuarios gestionar hábitos saludables, recibir recordatorios personalizados y guardar sus datos de forma persistente en almacenamiento local. 

Este proyecto corresponde al **Quinto Laboratorio del curso 1TEL05**, y ha sido implementado de forma individual siguiendo los requerimientos indicados por el curso.

---

## 🧩 Funcionalidades Implementadas

### 🟢 Ejercicio 1: Interfaz de Usuario (4 pts)

- ✅ **Pantalla de bienvenida personalizada**:
  - Saludo dinámico con el nombre del usuario.
  - Mensaje motivacional configurable.
  - Imagen de perfil seleccionada desde la galería y almacenada en Internal Storage.
  - Botones: *Ver mis hábitos* y *Configuraciones*.
  - Uso de `SharedPreferences` para guardar nombre y mensaje.

- ✅ **Listado de hábitos activos**:
  - RecyclerView con todos los hábitos registrados.
  - Cada hábito muestra: nombre, categoría, frecuencia (cada X horas), fecha y hora de inicio.
  - Botón para agregar nuevo hábito.

- ✅ **Pantalla de creación de hábito**:
  - Formulario para ingresar todos los datos del hábito.
  - Almacenamiento local del hábito para uso posterior.

---

### 🟡 Ejercicio 2: Notificaciones y Recordatorios (6 pts)

- ✅ **Notificaciones programadas por hábito**:
  - Cada hábito tiene una notificación periódica programada según su frecuencia.
  - Incluye nombre del hábito, acción sugerida e ícono representativo.

- ✅ **Canales de notificación por categoría**:
  - Canales: “Ejercicio”, “Alimentación”, “Sueño”, “Lectura”.
  - Configuraciones diferenciadas (IMPORTANCE_HIGH, vibración, etc.).
  - Compatible con versiones API >= 26.

- ✅ **Notificación motivacional configurable**:
  - El usuario puede definir un mensaje motivacional y su frecuencia de repetición.
  - Almacenado con `SharedPreferences`.

---

### 🔵 Ejercicio 3: Almacenamiento Local (5 pts)

- ✅ **Persistencia automática de hábitos**:
  - Cada vez que se añade, edita o elimina un hábito, los datos se guardan automáticamente usando `SharedPreferences` en formato JSON.

- ✅ **Carga automática al iniciar**:
  - Al iniciar la app, se cargan los hábitos guardados.
  - Si no existen hábitos, se muestra el mensaje: “No hay hábitos registrados”.

- ✅ **Eliminación de hábitos con confirmación**:
  - Al presionar el botón eliminar, se abre un diálogo de confirmación.
  - El hábito se elimina del RecyclerView y del almacenamiento.

---

## 🛠️ Tecnologías Utilizadas

- Java
- Android Studio
- SharedPreferences
- AlarmManager
- RecyclerView
- NotificationManager & Notification Channels
- Internal Storage
- Material Design (Buttons, Layouts)

---

## 🤖 Uso de Inteligencia Artificial

Durante el desarrollo de este laboratorio, se hizo uso de **IA generativa (ChatGPT de OpenAI)** para:
- Corregir errores de compilación y lógica.
- Mejorar la estructura del código Java y XML.
- Optimizar la organización visual de la interfaz (UI/UX).
- Implementar buenas prácticas de desarrollo en Android Studio.

---

## 🔗 Enlace al repositorio

[https://github.com/Nilorc/IOT_LAB5_20213745](https://github.com/Nilorc/IOT_LAB5_20213745)

---

## 📌 Nota

Este proyecto ha sido desarrollado de manera individual respetando las normas académicas de la PUCP.
