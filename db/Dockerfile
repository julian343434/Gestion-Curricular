FROM postgres:15

# Set environment variables for PostgreSQL
ENV POSTGRES_DB=gestion_curricular
ENV POSTGRES_USER=software1
ENV POSTGRES_PASSWORD=software1

# Copiar el script de inicialización desde la carpeta sql
COPY ./base.sql /docker-entrypoint-initdb.d/init.sql

# Configurar el puerto 5433 en el archivo de configuración
RUN echo "port = 5433" >> /usr/share/postgresql/postgresql.conf.sample

# Exponer el puerto 5433
EXPOSE 5433
