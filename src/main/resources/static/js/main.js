document.addEventListener('DOMContentLoaded', function() {
    const usernameInput = document.getElementById('username');
    const emailInput = document.getElementById('email');

    usernameInput.addEventListener('blur', function() {
        fetch(`/api/users/check-username?username=${usernameInput.value}`)
            .then(response => response.json())
            .then(data => {
                if (data.exists) {
                    alert('Username is already taken');
                }
            });
    });

    emailInput.addEventListener('blur', function() {
        fetch(`/api/users/check-email?email=${emailInput.value}`)
            .then(response => response.json())
            .then(data => {
                if (data.exists) {
                    alert('Email is already taken');
                }
            });
    });
});
