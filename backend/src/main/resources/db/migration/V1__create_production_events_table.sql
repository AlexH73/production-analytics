-- Create the production_events table.
-- Use the standard GENERATED AS IDENTITY syntax for an auto-incrementing 64-bit ID.

CREATE TABLE production_events (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_type VARCHAR(50) NOT NULL,   -- required field
    description VARCHAR(2000),         -- nullable
    occurred_at TIMESTAMP NOT NULL     -- required field
);

-- Add descriptions for the table and its columns.
COMMENT ON TABLE production_events IS 'Production events table';
COMMENT ON COLUMN production_events.id IS 'Unique auto-incrementing 64-bit identifier';
COMMENT ON COLUMN production_events.event_type IS 'Event type, required field';
COMMENT ON COLUMN production_events.description IS 'Event description, nullable';
COMMENT ON COLUMN production_events.occurred_at IS 'Date and time when the event occurred, required field';
