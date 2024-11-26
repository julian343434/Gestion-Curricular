# Usar la imagen oficial de PostgreSQL
FROM postgres:15

# Variables de entorno para inicializar la base de datos
ENV POSTGRES_DB=gestion_curricular
ENV POSTGRES_USER=software1
ENV POSTGRES_PASSWORD=software1

# Copiar el script de inicialización SQL
COPY ./baseDeDatos.sql /docker-entrypoint-initdb.d/init.sql

# Copiar el archivo de configuración postgresql.conf personalizado
COPY ./postgresql.conf /etc/postgresql/postgresql.conf

# Configurar PostgreSQL para usar el archivo de configuración personalizado
RUN echo "include '/etc/postgresql/postgresql.conf'" >> /usr/share/postgresql/postgresql.conf.sample

# Exponer el puerto PostgreSQL
EXPOSE 5433

# Comando de inicio
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]
