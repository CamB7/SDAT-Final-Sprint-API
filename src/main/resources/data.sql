-- Sample seed data for FlightTracker (MySQL)
-- Run this against your schema (ensure tables are created first by JPA or migrations)

-- Ensure flight table has flight_number and airline_name columns (MySQL 8+)
ALTER TABLE flight ADD COLUMN IF NOT EXISTS flight_number VARCHAR(32);
ALTER TABLE flight ADD COLUMN IF NOT EXISTS airline_name VARCHAR(128);

-- Airports
INSERT INTO airport (id, name, code) VALUES (1, 'Pearson International', 'YYZ') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (2, 'John F. Kennedy Intl', 'JFK') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (3, 'Heathrow', 'LHR') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (4, 'San Francisco Intl', 'SFO') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (5, 'Charles de Gaulle', 'CDG') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (6, 'Amsterdam Schiphol', 'AMS') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (7, 'Dubai Intl', 'DXB') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (8, 'Sydney Kingsford Smith', 'SYD') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);

-- Aircraft (include airline_name)
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (1, 'A320', 'Air Canada', 180) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (2, 'B737', 'Delta Air Lines', 160) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (3, 'A321', 'American Airlines', 190) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (4, 'A380', 'British Airways', 469) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (5, 'B747', 'Lufthansa', 416) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (6, 'B787', 'United Airlines', 242) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (7, 'B737 MAX', 'Southwest Airlines', 175) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (8, 'B777', 'Air France', 312) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (9, 'A350', 'KLM Royal Dutch', 300) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (10, 'A380', 'Emirates', 517) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (11, 'A330', 'Qantas', 297) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (12, 'B787', 'ANA', 242) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);

-- Gates (assign to airports)
INSERT INTO gate (id, code, terminal, airport_id) VALUES (1, 'A1', '1', 1) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (2, 'A2', '1', 1) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (3, 'B12', 'B', 2) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (4, 'C3', 'C', 2) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (5, 'T5', '5', 3) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (6, 'G7', 'G', 4) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (7, 'D1', 'D', 5) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (8, 'E2', 'E', 6) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (9, 'F4', 'F', 7) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (10, 'H1', 'H', 8) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);

-- Flights (20+), using realistic airline names and flight numbers
-- Columns: id, aircraft_id, airport_take_off_id, departure_gate_id, scheduled_departure, actual_departure, airport_landing_id, arrival_gate_id, scheduled_arrival, actual_arrival, flight_number, airline_name, status, last_updated
INSERT INTO flight (id, aircraft_id, airport_take_off_id, departure_gate_id, scheduled_departure, actual_departure, airport_landing_id, arrival_gate_id, scheduled_arrival, actual_arrival, flight_number, airline_name, status, last_updated) VALUES
(1, 1, 1, 1, '2026-04-14 06:00:00', '2026-04-14 06:10:00', 2, 3, '2026-04-14 09:00:00', '2026-04-14 09:05:00', 'AC101', 'Air Canada', 'AT_GATE', UNIX_TIMESTAMP()), -- Air Canada AC101 YYZ->JFK
(2, 2, 2, 3, '2026-04-14 07:30:00', '2026-04-14 07:35:00', 3, 5, '2026-04-14 19:30:00', NULL, 'DL202', 'Delta Air Lines', 'IN_FLIGHT', UNIX_TIMESTAMP()), -- Delta DL202 JFK->LHR
(3, 3, 2, 4, '2026-04-14 09:00:00', NULL, 4, 6, '2026-04-14 12:00:00', NULL, 'AA303', 'American Airlines', 'SCHEDULED', UNIX_TIMESTAMP()), -- American AA303 JFK->SFO
(4, 4, 3, 5, '2026-04-14 10:00:00', '2026-04-14 10:20:00', 5, 7, '2026-04-14 12:30:00', '2026-04-14 12:35:00', 'BA404', 'British Airways', 'AT_GATE', UNIX_TIMESTAMP()), -- British Airways BA404 LHR->CDG
(5, 5, 3, 5, '2026-04-14 11:00:00', NULL, 6, 8, '2026-04-14 13:30:00', NULL, 'LH505', 'Lufthansa', 'SCHEDULED', UNIX_TIMESTAMP()), -- Lufthansa LH505 LHR->AMS
(6, 6, 4, 6, '2026-04-14 12:30:00', NULL, 1, 2, '2026-04-14 20:30:00', NULL, 'UA606', 'United Airlines', 'SCHEDULED', UNIX_TIMESTAMP()), -- United UA606 SFO->YYZ
(7, 7, 4, 6, '2026-04-14 05:00:00', '2026-04-14 05:05:00', 4, 6, '2026-04-14 09:00:00', '2026-04-14 09:10:00', 'WN707', 'Southwest Airlines', 'DEPARTED', UNIX_TIMESTAMP()), -- Southwest WN707 SFO->SFO (short hop)
(8, 8, 5, 7, '2026-04-14 14:00:00', NULL, 3, 5, '2026-04-14 16:50:00', NULL, 'AF808', 'Air France', 'SCHEDULED', UNIX_TIMESTAMP()), -- Air France AF808 CDG->LHR
(9, 9, 6, 8, '2026-04-14 15:30:00', NULL, 7, 9, '2026-04-14 23:30:00', NULL, 'KL909', 'KLM Royal Dutch', 'SCHEDULED', UNIX_TIMESTAMP()), -- KLM KL909 AMS->DXB
(10, 10, 7, 9, '2026-04-14 02:00:00', NULL, 8, 10, '2026-04-14 18:00:00', NULL, 'EK010', 'Emirates', 'IN_FLIGHT', UNIX_TIMESTAMP()), -- Emirates EK010 DXB->SYD
(11, 11, 8, 10, '2026-04-14 20:00:00', NULL, 2, 3, '2026-04-15 06:00:00', NULL, 'QF111', 'Qantas', 'BOARDING', UNIX_TIMESTAMP()), -- Qantas QF111 SYD->JFK
(12, 12, 1, 2, '2026-04-14 03:30:00', '2026-04-14 03:45:00', 5, 7, '2026-04-14 07:30:00', NULL, 'NH212', 'ANA', 'DEPARTED', UNIX_TIMESTAMP()), -- ANA NH212 YYZ->CDG
(13, 1, 2, 3, '2026-04-14 08:15:00', NULL, 6, 8, '2026-04-14 11:00:00', NULL, 'AC131', 'Air Canada', 'SCHEDULED', UNIX_TIMESTAMP()), -- Air Canada AC131 JFK->AMS
(14, 2, 6, 8, '2026-04-14 22:00:00', NULL, 7, 9, '2026-04-15 06:00:00', NULL, 'DL141', 'Delta Air Lines', 'IN_FLIGHT', UNIX_TIMESTAMP()), -- Delta DL141 AMS->DXB
(15, 3, 5, 7, '2026-04-14 13:00:00', NULL, 2, 4, '2026-04-14 15:30:00', NULL, 'AA151', 'American Airlines', 'DELAYED', UNIX_TIMESTAMP()), -- American AA151 CDG->JFK
(16, 4, 3, 5, '2026-04-14 18:00:00', NULL, 8, 10, '2026-04-15 04:00:00', NULL, 'BA161', 'British Airways', 'SCHEDULED', UNIX_TIMESTAMP()), -- British Airways BA161 LHR->SYD
(17, 5, 1, 1, '2026-04-14 07:45:00', NULL, 4, 6, '2026-04-14 11:00:00', NULL, 'LH171', 'Lufthansa', 'SCHEDULED', UNIX_TIMESTAMP()), -- Lufthansa LH171 YYZ->SFO
(18, 6, 4, 6, '2026-04-14 23:00:00', NULL, 3, 5, '2026-04-15 07:00:00', NULL, 'UA181', 'United Airlines', 'SCHEDULED', UNIX_TIMESTAMP()), -- United UA181 SFO->LHR
(19, 7, 2, 4, '2026-04-14 16:00:00', NULL, 1, 2, '2026-04-14 19:30:00', NULL, 'WN191', 'Southwest Airlines', 'CANCELLED', UNIX_TIMESTAMP()), -- Southwest WN191 JFK->YYZ (cancelled)
(20, 8, 5, 7, '2026-04-14 09:45:00', '2026-04-14 09:55:00', 6, 8, '2026-04-14 11:30:00', '2026-04-14 11:35:00', 'AF202', 'Air France', 'AT_GATE', UNIX_TIMESTAMP()) -- Air France AF202 CDG->AMS
ON DUPLICATE KEY UPDATE aircraft_id=VALUES(aircraft_id), airport_take_off_id=VALUES(airport_take_off_id), departure_gate_id=VALUES(departure_gate_id), scheduled_departure=VALUES(scheduled_departure), actual_departure=VALUES(actual_departure), airport_landing_id=VALUES(airport_landing_id), arrival_gate_id=VALUES(arrival_gate_id), scheduled_arrival=VALUES(scheduled_arrival), actual_arrival=VALUES(actual_arrival), flight_number=VALUES(flight_number), airline_name=VALUES(airline_name), status=VALUES(status), last_updated=VALUES(last_updated);
