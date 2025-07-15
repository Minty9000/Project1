INSERT INTO Users (username, password, email, phone, role)
VALUES
('rep1', 'password123', 'rep1@example.com', '123-456-7890', 'rep'),
('admin1', 'adminpass', 'admin@example.com', '321-654-0987', 'admin'),
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