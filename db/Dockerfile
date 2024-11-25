FROM postgres:15

# Set environment variables for PostgreSQL
ENV POSTGRES_DB=gestion_curricular
ENV POSTGRES_USER=software1
ENV POSTGRES_PASSWORD=software1

# Copiar el script de inicialización desde la carpeta sql
COPY ./base.sql /docker-entrypoint-initdb.d/init.sql

# Expose PostgreSQL port
EXPOSE 5433
