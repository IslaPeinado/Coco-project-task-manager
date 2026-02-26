# COCO Design System

Bienvenido al sistema de diseño de **COCO Project Management Platform**. Este sistema proporciona directrices sobre los colores, tipografía y componentes reutilizables que se utilizan en la aplicación, tanto en **modo claro** como en **modo oscuro**.

## Guía de Colores

### Colores Principales

**Color Primario:**
- **#8B4513** (Madera)
  - Usos: Botones, enlaces, iconos principales, acción principal.
  
### Modo Claro

- **Fondo principal:** **#F7F4EF** (Arena clara)
- **Fondo secundario:** **#FFFFFF** (Blanco puro)
- **Bordes y sombras:** **#E6E1D8** (Gris claro)
- **Texto principal:** **#1F2937** (Negro)
- **Texto secundario:** **#6B7280** (Gris medio)
- **Botones y enlaces:** **#8B4513** (Madera)
- **Éxito:** **#16A34A** (Verde)
- **Advertencia:** **#F59E0B** (Amarillo)
- **Error:** **#DC2626** (Rojo)

### Modo Oscuro

- **Fondo principal:** **#1D1F20** (Negro suave)
- **Fondo secundario:** **#2C2F32** (Gris oscuro)
- **Bordes y sombras:** **#333638** (Gris oscuro)
- **Texto principal:** **#FFFFFF** (Blanco)
- **Texto secundario:** **#A9A9A9** (Gris claro)
- **Botones y enlaces:** **#8B4513** (Madera)
- **Éxito:** **#4CAF50** (Verde)
- **Advertencia:** **#FFC107** (Amarillo cálido)
- **Error:** **#F44336** (Rojo)

---

## Tipografía

Usamos una tipografía limpia y moderna para garantizar la legibilidad en todas las plataformas. La fuente principal es **Inter**, conocida por su flexibilidad y elegancia.

### Escala de Tipografía

- **Encabezados (h1-h3):**
  - h1: **32px**, semibold
  - h2: **28px**, semibold
  - h3: **24px**, semibold
- **Texto principal (p):**
  - **16px**, regular
- **Subtítulos / etiquetas:**
  - **14px**, semibold
- **Botones:**
  - **16px**, semibold

---

## Componentes Reutilizables

### Botones

- **Botón principal (primario):**
  - Fondo: **#8B4513** (Madera)
  - Texto: **#FFFFFF**
  - Hover: **#7A3E10**
  - Usos: Acción principal, confirmación.
  
- **Botón secundario:**
  - Fondo: **#FFFFFF**
  - Texto: **#8B4513**
  - Hover: **#E6F4EA**
  - Usos: Acción secundaria, cancelación.

### Campos de texto / Formularios

- **Entrada de texto:**
  - Fondo: **#FFFFFF** (Modo claro) / **#333638** (Modo oscuro)
  - Borde: **#E6E1D8** (Modo claro) / **#4A4A4A** (Modo oscuro)
  - Texto: **#1F2937** (Modo claro) / **#FFFFFF** (Modo oscuro)
  - Placeholder: **#A9A9A9**

- **Botón de envío:**
  - Fondo: **#8B4513** (Madera)
  - Texto: **#FFFFFF**

### Tarjetas / Panels

- **Fondo:**
  - Claro: **#FFFFFF**
  - Oscuro: **#2C2F32**
  
- **Bordes:** 
  - **#E6E1D8** (Modo claro) / **#4A4A4A** (Modo oscuro)
  
- **Sombras:**
  - **rgba(0, 0, 0, 0.1)** (suave, para crear jerarquías y profundidad)

### Indicadores de Estado

- **Éxito:** **#16A34A** (Verde)
- **Advertencia:** **#F59E0B** (Amarillo)
- **Error:** **#F44336** (Rojo)

### Iconos

- **Iconos principales:** Usar el mismo color que los textos principales.
- **Iconos secundarios:** **#A9A9A9** para iconos inactivos o deshabilitados.
- **Iconos de acción (hover):** **#8B4513** (Color de acción).

### Sombra y Profundidad

- **Sombra suave para modales y cards:**
  - **rgba(0, 0, 0, 0.1)** (opacidad ligera)

---

## Directrices de Interacción

- **Estado de carga (Loader):** Un círculo giratorio o barra de progreso que indique la carga de datos. Usar el color **#8B4513** para el loader y **#FFFFFF** como fondo.
  
- **Feedback del Usuario:**
  - **Éxito:** Mensaje con fondo verde claro (**#4CAF50**) y texto en blanco.
  - **Advertencia:** Fondo amarillo cálido (**#FFC107**) y texto en gris oscuro.
  - **Error:** Fondo rojo claro (**#F44336**) y texto blanco.

---

## Diseño de Interfaz en Modo Claro

### Pantallas

1. **Pantalla de Login:**
   - Fondo: **#F7F4EF**
   - Inputs de texto con borde **#E6E1D8**.
   - Botón de login: **#8B4513**.
   
2. **Pantalla de Dashboard:**
   - Fondo: **#FFFFFF**
   - Tarjetas de información: **#FFFFFF** con sombra suave.
   - Botones secundarios: **#FFFFFF** con borde **#8B4513**.

---

## Diseño de Interfaz en Modo Oscuro

### Pantallas

1. **Pantalla de Login:**
   - Fondo: **#1D1F20**
   - Inputs de texto con borde **#333638**.
   - Botón de login: **#8B4513**.

2. **Pantalla de Dashboard:**
   - Fondo: **#1D1F20**
   - Tarjetas de información: **#2C2F32** con sombra suave.
   - Botones secundarios: **#2C2F32** con borde **#8B4513**.

---

## Consideraciones Finales

Este sistema de diseño debe ser implementado de manera coherente en toda la aplicación para asegurar una experiencia de usuario fluida y profesional. Los colores, tipografía y componentes reutilizables están diseñados para ser escalables y adaptables a diferentes tamaños de pantalla y dispositivos. Asegúrate de mantener una consistencia entre el modo claro y el oscuro para evitar confusión en los usuarios.

