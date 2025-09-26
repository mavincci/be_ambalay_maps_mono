-- Manual rollback for V0001__Create_users_table.sql
-- Run manually if needed: psql -d db_pg_ambalay_maps_mono -f R0001__Drop_users_table.sql

DROP TABLE IF EXISTS mono.users CASCADE;
DROP EXTENSION IF EXISTS "uuid-ossp";