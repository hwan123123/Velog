document.addEventListener("DOMContentLoaded", function() {
    const userSettings = document.querySelector('.user-settings');
    const dropdownContent = userSettings.querySelector('.dropdown-content');

    userSettings.addEventListener('click', function() {
        dropdownContent.classList.toggle('show');
    });

    // Close the dropdown if the user clicks outside of it
    window.onclick = function(event) {
        if (!event.target.closest('.user-settings')) {
            if (dropdownContent.classList.contains('show')) {
                dropdownContent.classList.remove('show');
            }
        }
    }

    const logoutButton = document.getElementById('logout-button');
    if (logoutButton) {
        logoutButton.addEventListener('click', async () => {
            try {
                const response = await fetch('/logout', {
                    method: 'POST',
                    credentials: 'include' // 쿠키를 포함하여 요청
                });

                if (response.ok) {
                    alert('Successfully logged out');
                    // 필요에 따라 페이지를 리디렉션하거나 다른 동작 수행
                    window.location.href = '/velog'; // 메인 페이지로 리디렉션
                } else {
                    alert('Failed to log out');
                }
            } catch (error) {
                console.error('Error logging out:', error);
                alert('An error occurred while logging out');
            }
        });
    }
});
