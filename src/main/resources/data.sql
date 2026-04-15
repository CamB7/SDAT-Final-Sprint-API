-- Small Canadian test dataset for local MySQL
-- Airports
INSERT INTO airport (id, name, code) VALUES
  (1,'Toronto Pearson International Airport','YYZ'),
  (2,'Vancouver International Airport','YVR'),
  (3,'Montréal–Trudeau International Airport','YUL'),
  (4,'Calgary International Airport','YYC'),
  (5,'Edmonton International Airport','YEG');

-- Gates (each gate belongs to an airport)
INSERT INTO gate (id, code, terminal, airport_id) VALUES
  (1,'A01','T1',1),
  (2,'A02','T1',1),
  (3,'B01','T1',2),
  (4,'B02','T1',2),
  (5,'C01','T2',3),
  (6,'C02','T2',3),
  (7,'D01','T1',4),
  (8,'E01','T1',5);

-- Aircraft
INSERT INTO aircraft (id, flight_number, type, airline_name, number_of_seats) VALUES
  (1,'AC101','A320','Air Canada',150),
  (2,'WS202','B737','WestJet',160),
  (3,'PD303','E195','Porter Airlines',120),
  (4,'TS404','A321','Air Transat',180),
  (5,'FZ505','A320','Flair Airlines',150);

-- Join tables: which airports the aircraft operate from/to (takeoff & landing relations)
INSERT INTO aircraft_airport_takeoff (aircraft_id, airport_id) VALUES
  (1,1),(1,3),
  (2,1),(2,4),
  (3,1),(3,2),
  (4,3),(4,2),
  (5,4),(5,5);

INSERT INTO aircraft_airport_landing (aircraft_id, airport_id) VALUES
  (1,2),(1,3),
  (2,3),(2,1),
  (3,2),(3,3),
  (4,1),(4,5),
  (5,2),(5,4);

-- Flights (small set for testing). Use only the allowed statuses:
-- SCHEDULED, DELAYED, CANCELLED, BOARDING, DEPARTED, AT_GATE
-- Note: scheduled/actual times use MySQL DATETIME format (YYYY-MM-DD HH:MM:SS)
INSERT INTO flight (id, aircraft_id, airport_take_off_id, departure_gate_id, scheduled_departure, actual_departure, airport_landing_id, arrival_gate_id, scheduled_arrival, actual_arrival, flight_number, airline_name, status, last_updated) VALUES
  -- 1: future scheduled flight (no gate assigned yet)
  (1, 1, 1, NULL, '2026-04-20 09:30:00', NULL, 2, NULL, '2026-04-20 12:00:00', NULL, 'AC101', 'Air Canada', 'SCHEDULED', UNIX_TIMESTAMP()),
  -- 2: boarding at YYZ gate A02
  (2, 2, 1, 2, '2026-04-14 10:00:00', NULL, 4, NULL, '2026-04-14 12:30:00', NULL, 'WS202', 'WestJet', 'BOARDING', UNIX_TIMESTAMP()),
  -- 3: already departed
  (3, 3, 1, 1, '2026-04-14 06:00:00', '2026-04-14 06:05:00', 3, 5, '2026-04-14 07:30:00', '2026-04-14 07:25:00', 'PD303', 'Porter Airlines', 'DEPARTED', UNIX_TIMESTAMP()),
  -- 4: arrived and at gate at YYZ
  (4, 4, 3, NULL, '2026-04-13 14:00:00', '2026-04-13 14:05:00', 1, 1, '2026-04-13 15:30:00', '2026-04-13 15:25:00', 'TS404', 'Air Transat', 'AT_GATE', UNIX_TIMESTAMP()),
  -- 5: delayed flight
  (5, 5, 4, 7, '2026-04-14 16:00:00', NULL, 5, 8, '2026-04-14 17:15:00', NULL, 'FZ505', 'Flair Airlines', 'DELAYED', UNIX_TIMESTAMP()),
  -- 6: cancelled
  (6, 1, 1, NULL, '2026-04-15 08:00:00', NULL, 3, NULL, '2026-04-15 09:30:00', NULL, 'AC150', 'Air Canada', 'CANCELLED', UNIX_TIMESTAMP()),
  -- 7: scheduled Vancouver -> Toronto
  (7, 2, 2, 3, '2026-04-20 11:00:00', NULL, 1, NULL, '2026-04-20 18:00:00', NULL, 'WS250', 'WestJet', 'SCHEDULED', UNIX_TIMESTAMP()),
  -- 8: departed recently from YYC
  (8, 4, 4, 7, '2026-04-14 05:00:00', '2026-04-14 05:02:00', 2, 4, '2026-04-14 06:45:00', NULL, 'TS200', 'Air Transat', 'DEPARTED', UNIX_TIMESTAMP());

-- End of sample data
