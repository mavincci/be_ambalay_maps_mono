CREATE TABLE mono.user_sessions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES mono.users(id) ON DELETE CASCADE,
    refresh_token VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_sessions_user_id ON mono.user_sessions(user_id);
CREATE INDEX idx_user_sessions_refresh_token ON mono.user_sessions(refresh_token);
CREATE INDEX idx_user_sessions_expires_at ON mono.user_sessions(expires_at);