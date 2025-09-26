-- Manual rollback for V0002__Create_user_sessions_table.sql
-- Run manually if needed: psql -d db_pg_ambalay_maps_mono -f R0002__Drop_user_sessions_table.sql

DROP TABLE IF EXISTS mono.user_sessions;