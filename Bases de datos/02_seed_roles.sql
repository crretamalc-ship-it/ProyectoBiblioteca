-- ============================================================
-- Sistema de Biblioteca - Seed de roles
-- ============================================================
-- Inserta los roles base que necesita el flujo de autenticacion.
-- Si los roles no existen, security-service no puede asignar
-- nada y auth-service genera un JWT con roles vacios.
--
-- Ejecutar DESPUES de levantar security-service al menos una
-- vez (para que JPA cree la tabla 'roles' con ddl-auto=update),
-- o despues de aplicar el script 01_create_databases.sql.
-- ============================================================


USE library_security_db;

-- Crear la tabla roles si security-service todavia no la creo
-- (espejo de la entidad com.biblioteca.security_service.model.Rol)
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_roles_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insercion idempotente: si el rol ya existe, no se duplica.
INSERT INTO roles (nombre) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_SOCIO'),
    ('ROLE_BIBLIOTECARIO')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- ============================================================
-- Verificacion: deberian aparecer los 3 roles
-- ============================================================
SELECT id, nombre FROM roles ORDER BY id;
