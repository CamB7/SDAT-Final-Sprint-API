-- Airports (mostly Canadian; a few US hubs)
INSERT INTO airport (id, name, code) VALUES
(1, 'Toronto Pearson International', 'YYZ'),
(2, 'Vancouver International', 'YVR'),
(3, 'Montréal–Trudeau', 'YUL'),
(4, 'Calgary International', 'YYC'),
(5, 'Edmonton International', 'YEG'),
(6, 'Halifax Stanfield', 'YHZ'),
(7, 'John F. Kennedy Intl', 'JFK'),
(8, 'Los Angeles Intl', 'LAX'),
(9, 'San Francisco Intl', 'SFO'),
(10, 'Chicago O''Hare Intl', 'ORD')
ON DUPLICATE KEY UPDATE name=VALUES(name), code=VALUES(code);

-- Aircraft (include airline_name; some Canadian carriers first)
INSERT INTO aircraft (id, type, airline_name, number_of_seats) VALUES
(1, 'A320', 'Air Canada', 160),
(2, 'B737', 'WestJet', 160),
(3, 'Q400', 'Jazz Aviation', 78),
(4, 'E195', 'Porter Airlines', 120),
(5, 'A321', 'Air Canada', 190),
(6, 'B737 MAX', 'WestJet Encore', 150),
(7, 'B737', 'American Airlines', 160),
(8, 'B757', 'Delta Air Lines', 200),
(9, 'B767', 'United Airlines', 220),
(10, 'A220', 'Air Canada', 125),
(11, 'A321', 'Air Transat', 220),
(12, 'A320', 'Swoop', 174)
ON DUPLICATE KEY UPDATE type=VALUES(type), airline_name=VALUES(airline_name), number_of_seats=VALUES(number_of_seats);

-- Gates (assign to airports)
INSERT INTO gate (id, code, terminal, airport_id) VALUES
(1, 'T1-A1', 'T1', 1),
(2, 'T1-A2', 'T1', 1),
(3, 'C3-G5', 'C3', 2),
(4, 'C3-G6', 'C3', 2),
(5, 'M1-1', 'M1', 3),
(6, 'C1-2', 'C1', 4),
(7, 'E2-5', 'E2', 5),
(8, 'H1-7', 'H1', 6),
(9, 'J1-9', 'J1', 7),
(10, 'L1-4', 'L1', 8)
ON DUPLICATE KEY UPDATE code=VALUES(code), terminal=VALUES(terminal), airport_id=VALUES(airport_id);

-- Flights (20) - North American only (mostly Canadian routes plus some US cross-border)
-- Allowed statuses: SCHEDULED, DELAYED, CANCELLED, BOARDING, DEPARTED, AT_GATE
INSERT INTO flight (id, aircraft_id, airport_take_off_id, departure_gate_id, scheduled_departure, actual_departure, airport_landing_id, arrival_gate_id, scheduled_arrival, actual_arrival, flight_number, airline_name, status, last_updated) VALUES
(1, 1, 1, 1, '2026-04-14 06:30:00', '2026-04-14 06:40:00', 7, 9, '2026-04-14 08:45:00', '2026-04-14 08:50:00', 'AC100', 'Air Canada', 'AT_GATE', UNIX_TIMESTAMP()),
(2, 2, 2, 3, '2026-04-14 07:00:00', NULL, 1, 2, '2026-04-14 09:50:00', NULL, 'WS210', 'WestJet', 'SCHEDULED', UNIX_TIMESTAMP()),
(3, 3, 3, 5, '2026-04-14 07:45:00', NULL, 1, 1, '2026-04-14 09:10:00', NULL, 'QZ305', 'Jazz Aviation', 'DELAYED', UNIX_TIMESTAMP()),
(4, 4, 1, 2, '2026-04-14 08:15:00', NULL, 3, 5, '2026-04-14 09:40:00', NULL, 'PD120', 'Porter Airlines', 'BOARDING', UNIX_TIMESTAMP()),
(5, 5, 4, 6, '2026-04-14 08:30:00', NULL, 2, 4, '2026-04-14 09:50:00', NULL, 'AC250', 'Air Canada', 'SCHEDULED', UNIX_TIMESTAMP()),
(6, 6, 5, 7, '2026-04-14 09:00:00', NULL, 1, 2, '2026-04-14 10:35:00', NULL, 'WS330', 'WestJet Encore', 'SCHEDULED', UNIX_TIMESTAMP()),
(7, 7, 1, 1, '2026-04-14 09:30:00', NULL, 8, 10, '2026-04-14 12:15:00', NULL, 'AA410', 'American Airlines', 'CANCELLED', UNIX_TIMESTAMP()),
(8, 8, 8, 10, '2026-04-14 10:00:00', NULL, 1, 2, '2026-04-14 15:05:00', NULL, 'DL505', 'Delta Air Lines', 'SCHEDULED', UNIX_TIMESTAMP()),
(9, 9, 1, 2, '2026-04-14 10:30:00', NULL, 4, 6, '2026-04-14 12:45:00', NULL, 'UA606', 'United Airlines', 'DELAYED', UNIX_TIMESTAMP()),
(10, 10, 2, 3, '2026-04-14 11:00:00', NULL, 9, 10, '2026-04-14 13:20:00', NULL, 'AC720', 'Air Canada', 'SCHEDULED', UNIX_TIMESTAMP()),
(11, 11, 3, 5, '2026-04-14 11:45:00', NULL, 1, 1, '2026-04-14 14:05:00', NULL, 'TS811', 'Air Transat', 'SCHEDULED', UNIX_TIMESTAMP()),
(12, 12, 4, 6, '2026-04-14 12:30:00', NULL, 2, 4, '2026-04-14 14:10:00', NULL, 'SO912', 'Swoop', 'BOARDING', UNIX_TIMESTAMP()),
(13, 1, 5, 7, '2026-04-14 13:00:00', NULL, 2, 3, '2026-04-14 14:20:00', NULL, 'AC135', 'Air Canada', 'SCHEDULED', UNIX_TIMESTAMP()),
(14, 2, 6, 8, '2026-04-14 13:45:00', NULL, 3, 5, '2026-04-14 15:15:00', NULL, 'WS241', 'WestJet', 'DELAYED', UNIX_TIMESTAMP()),
(15, 3, 1, 1, '2026-04-14 14:30:00', NULL, 10, 9, '2026-04-14 16:50:00', NULL, 'QZ315', 'Jazz Aviation', 'CANCELLED', UNIX_TIMESTAMP()),
(16, 4, 2, 4, '2026-04-14 15:00:00', NULL, 7, 9, '2026-04-14 17:30:00', NULL, 'PD140', 'Porter Airlines', 'SCHEDULED', UNIX_TIMESTAMP()),
(17, 5, 3, 5, '2026-04-14 15:45:00', NULL, 6, 8, '2026-04-14 17:10:00', NULL, 'AC177', 'Air Canada', 'BOARDING', UNIX_TIMESTAMP()),
(18, 6, 4, 6, '2026-04-14 16:30:00', '2026-04-14 16:35:00', 1, 2, '2026-04-14 18:55:00', '2026-04-14 19:00:00', 'WS360', 'WestJet Encore', 'DEPARTED', UNIX_TIMESTAMP()),
(19, 7, 9, 10, '2026-04-14 17:15:00', NULL, 1, 1, '2026-04-14 20:00:00', NULL, 'AA191', 'American Airlines', 'SCHEDULED', UNIX_TIMESTAMP()),
(20, 8, 10, 9, '2026-04-14 18:00:00', NULL, 2, 3, '2026-04-14 21:20:00', NULL, 'DL202', 'Delta Air Lines', 'AT_GATE', UNIX_TIMESTAMP())
ON DUPLICATE KEY UPDATE
  aircraft_id=VALUES(aircraft_id),
  airport_take_off_id=VALUES(airport_take_off_id),
  departure_gate_id=VALUES(departure_gate_id),
  scheduled_departure=VALUES(scheduled_departure),
  actual_departure=VALUES(actual_departure),
  airport_landing_id=VALUES(airport_landing_id),
  arrival_gate_id=VALUES(arrival_gate_id),
  scheduled_arrival=VALUES(scheduled_arrival),
  actual_arrival=VALUES(actual_arrival),
  flight_number=VALUES(flight_number),
  airline_name=VALUES(airline_name),
  status=VALUES(status),
  last_updated=VALUES(last_updated);
