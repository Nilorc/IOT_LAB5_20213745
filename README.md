# 📱 LAB5 + LAB7 - Mis Hábitos (App Android con Almacenamiento en la Nube)

### Curso: Servicios y Aplicaciones para IoT [1TEL05]  
### Semestre: 2025-1  
### Estudiante: Nilo Rikel Cori Ramos  
### Código PUCP: 20213745  
### Fecha de entrega: 01/07/2025

---

## 📱 Descripción del Proyecto

**Mis Hábitos** es una aplicación móvil Android desarrollada en **Java con Android Studio**, que permite a los usuarios crear, visualizar y gestionar sus hábitos saludables, además de recibir notificaciones programadas como recordatorios.

Este proyecto fue originalmente desarrollado para el **Laboratorio 5**, y ha sido extendido para cumplir con los requerimientos del **Laboratorio 7**, implementando funcionalidad de **almacenamiento en la nube con Cloudinary**.

---

## 🧩 Funcionalidades Implementadas

### ✅ Ejercicios del Lab 5: Gestión de Hábitos y Recordatorios

#### 🟢 Ejercicio 1: Interfaz de Usuario

- Pantalla de bienvenida con nombre, frase motivacional e imagen de perfil.
- Visualización de hábitos en RecyclerView.
- Formulario para crear un nuevo hábito.

#### 🟡 Ejercicio 2: Notificaciones

- Notificaciones periódicas por hábito usando `AlarmManager`.
- Canales de notificación según categoría (Ejercicio, Lectura, Sueño, etc.).
- Mensajes motivacionales configurables.

#### 🔵 Ejercicio 3: Persistencia local

- Guardado y restauración automática de hábitos en `SharedPreferences` (JSON).
- Eliminación de hábitos con confirmación.
- Mensaje cuando no hay hábitos registrados.

---

### ☁️ Funcionalidades del Lab 7: Almacenamiento en la Nube (Cloudinary)

Se ha añadido una vista específica para el laboratorio 7 que permite trabajar con imágenes en la nube:

#### ✅ Subida de Fotos
- Permite seleccionar una imagen desde la galería.
- La imagen se **sube automáticamente** a **Cloudinary** en la carpeta `Fotos_lab7`.
- Se muestra la imagen seleccionada en una vista previa (`ImageView`).
- La **URL pública** generada se muestra en un campo editable para copiarla fácilmente.
- Se muestra un `Toast` con el enlace de la imagen al subir exitosamente.

#### ✅ Visualización de Imágenes desde la Nube
- Al ingresar manualmente una URL válida, se puede **mostrar la imagen directamente** desde Cloudinary.
- La imagen se renderiza en el mismo `ImageView`.

#### ✅ Descarga al dispositivo
- Una vez subida o visualizada una imagen, se puede **descargar al almacenamiento del dispositivo** con un solo clic.
- Si no hay imagen cargada o URL válida, se muestran advertencias.

#### ✅ Validaciones incluidas
- Verificación de que la URL sea válida antes de mostrar.
- Evita intentar descargar si no hay imagen cargada o válida.
- Mensajes claros al usuario en caso de errores o acciones inválidas.

---

## 🛠️ Tecnologías Utilizadas

- Java
- Android Studio
- SharedPreferences (persistencia local)
- AlarmManager (recordatorios)
- NotificationManager (notificaciones)
- Cloudinary API (almacenamiento en la nube)
- Picasso (renderizado de imágenes desde URL)
- DownloadManager (descarga al dispositivo)
- Material Design (componentes visuales)

---

## 🤖 Uso de Inteligencia Artificial

Durante el desarrollo del laboratorio 5 y 7, se utilizó **ChatGPT de OpenAI** como asistente para:
- Detectar errores y refactorizar código.
- Optimizar la carga y subida de imágenes.
- Mejorar el diseño UI siguiendo prácticas de Material Design.
- Automatizar flujos con manejo de URI e integración segura con Cloudinary.

---

## 🔗 Enlace al Repositorio

[https://github.com/Nilorc/IOT_LAB5_20213745](https://github.com/Nilorc/IOT_LAB5_20213745)

---

## 📌 Nota

Este proyecto ha sido desarrollado de manera individual, respetando las normas académicas de la PUCP y los principios de integridad en la evaluación.
