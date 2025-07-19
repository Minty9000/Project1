CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    role ENUM('admin', 'customer', 'rep') NOT NULL
);
CREATE TABLE CustomerReps (
    rep_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20)
);
CREATE TABLE TransitLines (
    line_id INT PRIMARY KEY AUTO_INCREMENT,
    line_name VARCHAR(100) UNIQUE NOT NULL
);
CREATE TABLE Reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    line_id INT,
    reservation_date DATE,
    amount DECIMAL(10,2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (line_id) REFERENCES TransitLines(line_id)
);
CREATE TABLE Train (
    train_id INT PRIMARY KEY,
    transit_line VARCHAR(100) NOT NULL
);
CREATE TABLE CustomerReservation (
    reservation_number INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,          -- Customer name
    train_id INT NOT NULL,               -- Foreign key to Train
    price DECIMAL(10,2) NOT NULL,        -- Reservation price
    reservation_date DATE NOT NULL,      -- Date of reservation
    FOREIGN KEY (train_id) REFERENCES Train(train_id)
);
CREATE TABLE Trainlines (
    tlid INT PRIMARY KEY auto_increment,
    name VARCHAR(100) NOT NULL,
    origin int,
    destination int,
    travelTime int,
    numOfStops int,
    fare float,
    FOREIGN KEY (origin) REFERENCES stations (sid),
    FOREIGN KEY (destination) REFERENCES stations (sid)
);


CREATE TABLE Stops (
    tlid INT,
    sid INT,
    travelTime int,
    stopNumber int,
    PRIMARY KEY (tlid, sid),
    FOREIGN KEY (tlid) REFERENCES trainlines (tlid),
    FOREIGN KEY (sid) REFERENCES stations (sid)
);

CREATE TABLE stations (
	sid INT PRIMARY KEY auto_increment,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL
);

INSERT INTO stations (name, city, state)
VALUES
  ('Bay Head', 'Bay Head', 'NJ'),
  ('Point Pleasant Beach', 'Point Pleasant', 'NJ'),
  ('Manasquan', 'Manasquan', 'NJ'),
  ('Spring Lake', 'Spring Lake', 'NJ'),
  ('Belmar', 'Belmar', 'NJ'),
  ('Bradley Beach', 'Bradley Beach', 'NJ'),
  ('Asbury Park', 'Asbury Park', 'NJ'),
  ('Allenhurst', 'Allenhurst', 'NJ'),
  ('Elberon', 'Elberon', 'NJ'),
  ('Long Branch', 'Long Branch', 'NJ'),
  ('Monmouth Park', 'Monmouth Park', 'NJ'),
  ('Little Silver', 'Little Silver', 'NJ'),
  ('Red Bank', 'Red Bank', 'NJ'),
  ('Middletown', 'Middletown', 'NJ'),
  ('Hazlet', 'Hazlet', 'NJ'),
  ('Aberdeen-Matawan', 'Aberdeen', 'NJ'),
  ('South Amboy', 'South Amboy', 'NJ'),
  ('Perth Amboy', 'Perth Amboy', 'NJ'),
  ('Woodbridge', 'Woodbridge', 'NJ'),
  ('Avenel', 'Avenel', 'NJ'),
  ('Elizabeth', 'Elizabeth', 'NJ'),
  ('North Elizabeth', 'North Elizabeth', 'NJ'),
  ('Newark Liberty International Airport', 'Newark', 'NJ'),
  ('Newark Penn Station', 'Newark', 'NJ'),
  ('New York Penn Station', 'New York', 'NY');
INSERT INTO Users (username, password, email, phone, role)
VALUES
('rep1', 'Represent1223', 'rep1@example.com', '123-456-7890', 'rep'),
('admin1', 'TrainAdmin1223', 'admin@example.com', '321-654-0987', 'admin'),
('cust1', 'custpass', 'cust1@example.com', '111-222-3333', 'customer');

-- Insert Trains
INSERT INTO Train (train_id, transit_line) VALUES
(1, 'Blue Line'),
(2, 'Green Line'),
(3, 'Red Line'),
(4, 'Yellow Line'),
(5, 'Purple Line');

-- Insert Customer Reservations
INSERT INTO CustomerReservation (name, train_id, price, reservation_date) VALUES
('Alice', 1, 15.00, '2024-07-10'),
('Bob', 2, 12.50, '2024-07-12'),
('Alice', 1, 15.00, '2024-08-01'),
('Charlie', 3, 20.00, '2024-08-02'),
('Diana', 4, 18.00, '2024-08-05'),
('Alice', 5, 22.00, '2024-08-05'),
('Bob', 2, 12.50, '2024-08-06'),
('Charlie', 2, 12.50, '2024-08-06'),
('Eve', 1, 15.00, '2024-08-06'),
('Frank', 3, 20.00, '2024-08-07');