document.addEventListener('DOMContentLoaded', function () {
    // Check for login error in URL
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        showToast('Invalid username or password.', statusColors.ERROR);
    }

    // Session expired response
    if (sessionExpired) {
        printStatusResponse(sessionExpired);
    }

    // Reset password response
    if (resetResponse) {
        printStatusResponse(resetResponse);
    }
});

function handleLoginSubmit(event) {
    const button = document.getElementById('signInBtn');
    const spinner = document.getElementById('spinner');
    const signInText = document.getElementById('signInText');

    // Show spinner and disable button
    spinner.classList.remove('hidden');
    signInText.textContent = 'Signing In…';
    button.disabled = true;
}

function togglePasswordVisibility() {
    const input = document.getElementById('floating_password');
    const icon = document.getElementById('passwordToggleIcon');
    const contextPath = document.querySelector('meta[name="context-path"]').getAttribute('content');


    const isPassword = input.type === 'password';
    input.type = isPassword ? 'text' : 'password';
    icon.src = isPassword ? `${contextPath}student/images/view.svg` : `${contextPath}student/images/close-eye.svg`;
    icon.alt = isPassword ? 'Hide password' : 'Show password';
}