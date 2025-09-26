-- Reference data that can be updated
-- This runs every time the file changes

-- Upsert approach for repeatable data
INSERT INTO mono.users (public_id, email, password_hash, first_name, last_name, role, created_at, updated_at) 
VALUES 
    (uuid_generate_v4()::text, 'system@ambalay.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'System', 'User', 'SYSTEM_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO UPDATE SET
    first_name = EXCLUDED.first_name,
    last_name = EXCLUDED.last_name,
    role = EXCLUDED.role,
    updated_at = CURRENT_TIMESTAMP;