CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS Segment (
    id UUID DEFAULT uuid_generate_v4() NOT NULL,  
    number VARCHAR(255),
    length DECIMAL(10, 2),
    direction_nomenclature VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Calzada (
    id UUID DEFAULT uuid_generate_v4() NOT NULL,  
    length DECIMAL(10, 2),
    segment_id UUID,  
    PRIMARY KEY (id),
    FOREIGN KEY (segment_id) REFERENCES Segment(id) ON DELETE CASCADE  
);

CREATE TABLE IF NOT EXISTS Bordillo (
    id UUID DEFAULT uuid_generate_v4() NOT NULL,  
    length DECIMAL(10, 2),
    segment_id UUID,  
    PRIMARY KEY (id),
    FOREIGN KEY (segment_id) REFERENCES Segment(id) ON DELETE CASCADE  
);
