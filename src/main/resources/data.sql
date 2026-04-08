-- Sample seed data for FlightTracker (MySQL)
-- Run this against your schema (ensure tables are created first by JPA or migrations)

-- Airports
INSERT INTO airport (id, name, code) VALUES (1, 'Pearson International', 'YYZ') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (2, 'John F. Kennedy Intl', 'JFK') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (3, 'Heathrow', 'LHR') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
INSERT INTO airport (id, name, code) VALUES (4, 'San Francisco Intl', 'SFO') ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);

-- Aircraft
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (1, 'A320', 'AirKeyin', 180) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (2, 'B737', 'KeyAir', 160) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (3, 'A380', 'GlobalAir', 520) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES (4, 'B787', 'DreamAir', 240) ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);

-- Gates (per airport)
INSERT INTO gate (id, code, terminal, airport_id) VALUES (1, 'A1', '1', 1) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (2, 'A2', '1', 1) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (3, 'B12', 'B', 2) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (4, 'C3', 'C', 2) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (5, 'T5', '5', 3) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);
INSERT INTO gate (id, code, terminal, airport_id) VALUES (6, 'G7', 'G', 4) ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);

-- Flights
-- Note: flight table column names match JPA-mapped fields. Adjust if your generated schema uses different names.
-- Times are example timestamps — adjust to your timezone/data as needed.
INSERT INTO flight (id, aircraft_id, airport_take_off_id, departure_gate_id, scheduled_departure, actual_departure, airport_landing_id, arrival_gate_id, scheduled_arrival, actual_arrival, status, last_updated) VALUES
-- YYZ arrivals / departures
(1, 1, NULL, NULL, '2026-04-07 06:00:00', '2026-04-07 06:10:00', 1, 1, '2026-04-07 08:00:00', '2026-04-07 08:05:00', 'AT_GATE', UNIX_TIMESTAMP()),
(2, 1, NULL, NULL, '2026-04-07 12:00:00', NULL, 1, NULL, '2026-04-07 14:00:00', NULL, 'IN_FLIGHT', UNIX_TIMESTAMP()),
(3, 1, 1, 2, '2026-04-07 15:00:00', NULL, NULL, NULL, '2026-04-07 18:00:00', NULL, 'SCHEDULED', UNIX_TIMESTAMP()),
(4, 2, 1, 1, '2026-04-08 09:30:00', '2026-04-08 09:35:00', NULL, NULL, '2026-04-08 11:30:00', NULL, 'DEPARTED', UNIX_TIMESTAMP()),
(5, 2, NULL, NULL, '2026-04-08 04:00:00', NULL, 1, 2, '2026-04-08 06:00:00', NULL, 'LANDED', UNIX_TIMESTAMP()),
-- JFK flights
(6, 3, NULL, NULL, '2026-04-08 07:00:00', NULL, 2, 3, '2026-04-08 09:30:00', NULL, 'IN_FLIGHT', UNIX_TIMESTAMP()),
(7, 4, 2, 4, '2026-04-08 10:00:00', NULL, NULL, NULL, '2026-04-08 14:00:00', NULL, 'SCHEDULED', UNIX_TIMESTAMP()),
-- LHR flights
(8, 3, NULL, NULL, '2026-04-08 01:00:00', '2026-04-08 01:20:00', 3, 5, '2026-04-08 03:50:00', '2026-04-08 03:55:00', 'AT_GATE', UNIX_TIMESTAMP()),
(9, 4, 3, 5, '2026-04-08 13:00:00', NULL, NULL, NULL, '2026-04-08 16:00:00', NULL, 'SCHEDULED', UNIX_TIMESTAMP()),
-- SFO flights
(10, 2, NULL, NULL, '2026-04-08 05:00:00', NULL, 4, 6, '2026-04-08 11:00:00', NULL, 'IN_FLIGHT', UNIX_TIMESTAMP()),
(11, 1, 4, 6, '2026-04-08 20:00:00', NULL, NULL, NULL, '2026-04-09 00:30:00', NULL, 'BOARDING', UNIX_TIMESTAMP()),
(12, 4, 4, NULL, '2026-04-08 02:00:00', NULL, 1, NULL, '2026-04-08 08:00:00', NULL, 'CANCELLED', UNIX_TIMESTAMP()),
(13, 3, NULL, NULL, '2026-04-08 16:00:00', NULL, 2, NULL, '2026-04-08 20:00:00', NULL, 'DELAYED', UNIX_TIMESTAMP())
ON DUPLICATE KEY UPDATE aircraft_id=VALUES(aircraft_id), airport_take_off_id=VALUES(airport_take_off_id), departure_gate_id=VALUES(departure_gate_id), scheduled_departure=VALUES(scheduled_departure), actual_departure=VALUES(actual_departure), airport_landing_id=VALUES(airport_landing_id), arrival_gate_id=VALUES(arrival_gate_id), scheduled_arrival=VALUES(scheduled_arrival), actual_arrival=VALUES(actual_arrival), status=VALUES(status), last_updated=VALUES(last_updated);

-- Populate ManyToMany join tables (aircraft <-> airport) derived from flights above
-- aircraft_airport_takeoff (aircraft_id, airport_id)
INSERT IGNORE INTO aircraft_airport_takeoff (aircraft_id, airport_id) VALUES
(1,1),
(2,1),
(4,2),
(4,3),
(1,4),
(4,4);

-- aircraft_airport_landing (aircraft_id, airport_id)
INSERT IGNORE INTO aircraft_airport_landing (aircraft_id, airport_id) VALUES
(1,1),
(2,1),
(3,2),
(3,3),
(2,4),
(4,1);

