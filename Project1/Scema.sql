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