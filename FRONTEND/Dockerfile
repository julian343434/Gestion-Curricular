# Usa una imagen base de Node.js para la etapa de construcción
FROM node:18 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto
COPY package.json package-lock.json ./

# Instala las dependencias
RUN npm install

# Copia el resto de los archivos
COPY . .

# Compila la aplicación para producción
RUN npm run build

# Usa una imagen ligera de NGINX para servir la aplicación
FROM nginx:alpine

# Copia los archivos construidos desde la etapa de construcción
COPY --from=build /app/dist /usr/share/nginx/html

# Expone el puerto en el que NGINX estará escuchando
EXPOSE 5173

# Cambia la configuración de NGINX para escuchar en el puerto 5173
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Comando para iniciar NGINX
CMD ["nginx", "-g", "daemon off;"]
