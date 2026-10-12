const form = document.getElementById('bookingForm');
const message = document.getElementById('message');
const body = document.getElementById('bookingsBody'); // tbody for bookings table

form.addEventListener('submit', async e => {
    e.preventDefault();
    message.className = '';
    message.textContent = '';

    const formData = new FormData(form);
    try {
        const response = await fetch('bookings', {
            method: 'POST',
            body: formData
        });
        const result = await response.json();
        if (result.success) {
            message.className = 'success';
            message.textContent = result.success;
            loadBookings(); // refresh table after booking
        } else {
            message.className = 'error';
            message.textContent = result.error || 'Could not save booking';
        }
    } catch (err) {
        message.className = 'error';
        message.textContent = 'Error connecting to server';
        console.error(err);
    }
});

async function loadBookings() {
    body.innerHTML = '<tr><td colspan="7">Loading...</td></tr>';
    try {
        const response = await fetch('bookings');
        const bookings = await response.json();
        body.innerHTML = '';
        bookings.forEach(booking => {
            const row = document.createElement('tr');
            // ✅ corrected fields to match servlet JSON
            row.innerHTML = `
                <td>${booking.id}</td>
                <td>${booking.name}</td>
                <td>${booking.phone}</td>
                <td>${booking.service}</td>
                <td>${booking.date}</td>
                <td>${booking.time}</td>
                <td>${booking.address}</td>
            `;
            body.appendChild(row);
        });
    } catch (err) {
        body.innerHTML = '<tr><td colspan="7">Could not load bookings</td></tr>';
        console.error(err);
    }
}

document.getElementById('refreshBtn').addEventListener('click', loadBookings);

// Load bookings on page start
loadBookings();
