-- ============================================================
-- Sistema de Biblioteca - Creacion de bases de datos
-- ============================================================
-- Cada microservicio tiene su propia base de datos para mantener
-- el principio de "Database per Service" de la arquitectura de
-- microservicios.
-- ============================================================

-- 1. auth-service: registros de login y emision de JWT
CREATE DATABASE IF NOT EXISTS library_auth_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. user-service: CRUD de usuarios (socios, bibliotecarios, admins)
CREATE DATABASE IF NOT EXISTS library_users_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 3. security-service: roles y asignaciones por usuario
CREATE DATABASE IF NOT EXISTS library_security_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 4. ms-inventario: catalogo de libros
CREATE DATABASE IF NOT EXISTS library_inventory_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 5. ms-prestamos: registro de prestamos de libros
CREATE DATABASE IF NOT EXISTS library_loans_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 6. ms-multas: multas por devoluciones tardias
CREATE DATABASE IF NOT EXISTS library_fines_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 7. ms-favoritos-listas: listas personales de favoritos
CREATE DATABASE IF NOT EXISTS library_favorites_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 8. ms-notificaciones: avisos por vencimiento, multas, reservas
CREATE DATABASE IF NOT EXISTS library_notifications_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 9. ms-sugerencias: propuestas de libros para comprar
CREATE DATABASE IF NOT EXISTS library_suggestions_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 10. ms-valoraciones: reviews y rating de libros
CREATE DATABASE IF NOT EXISTS library_reviews_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ============================================================
-- Verificacion: listar las bases creadas
-- ============================================================
SHOW DATABASES LIKE 'library_%';
