-- Создание таблицы production_events
-- Используется стандартный синтаксис GENERATED AS IDENTITY для автоинкремента 64-битного id.
-- Для СУБД, не поддерживающих этот синтаксис (например, MySQL), можно заменить на BIGINT AUTO_INCREMENT,
-- для PostgreSQL — на BIGSERIAL.

CREATE TABLE IF NOT EXISTS production_events (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,   -- обязательное поле
    description TEXT,                   -- может быть NULL
    occurred_at TIMESTAMP NOT NULL      -- обязательное поле
);

-- При необходимости можно добавить комментарии к таблице и столбцам:
COMMENT ON TABLE production_events IS 'Таблица событий производства';
COMMENT ON COLUMN production_events.id IS 'Уникальный идентификатор, 64-битное автоинкрементное число';
COMMENT ON COLUMN production_events.event_type IS 'Тип события, обязательное поле';
COMMENT ON COLUMN production_events.description IS 'Описание события, может быть NULL';
COMMENT ON COLUMN production_events.occurred_at IS 'Дата и время наступления события, обязательное поле';