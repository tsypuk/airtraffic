ALTER TABLE `boards`
ADD UNIQUE INDEX `hex_flights` (`hex` ASC, `flight` ASC);