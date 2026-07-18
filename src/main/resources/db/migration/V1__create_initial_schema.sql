CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS task (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255),
    description VARCHAR(255),
    completed BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_conversation (
    id UUID PRIMARY KEY,
    title VARCHAR(255),
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6)
);

CREATE TABLE IF NOT EXISTS chat_message (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_id UUID NOT NULL,
    role VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT fk_chat_message_conversation
        FOREIGN KEY (conversation_id)
        REFERENCES chat_conversation (id)
);
