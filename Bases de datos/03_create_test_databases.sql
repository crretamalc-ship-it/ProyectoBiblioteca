-- ============================================================
-- Sistema de Biblioteca - Creacion de bases de datos de prueba
-- ============================================================
-- Estas bases son exclusivas para tests o pruebas de integracion.
-- No deben usarse para desarrollo manual ni para datos reales.
--
-- Nota: los tests automatizados del proyecto ya usan H2 en memoria
-- desde src/test/resources/application.properties. Este script queda
-- disponible si se quiere probar contra MySQL de forma aislada.
-- ============================================================

CREATE DATABASE IF NOT EXISTS library_auth_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS library_users_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS library_security_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS library_inventory_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS library_loans_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS library_fines_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS library_favorites_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS library_notifications_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS library_suggestions_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS library_reviews_test_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ============================================================
-- Verificacion: listar las bases de prueba creadas
-- ============================================================
SHOW DATABASES LIKE 'library_%_test_db';
