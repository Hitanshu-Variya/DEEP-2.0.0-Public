if(uploadError) {
    printStatusResponse(uploadError);
}

if(uploadWarning) {
    printStatusResponse(uploadWarning);
}

if(uploadSuccess) {
    printStatusResponse(uploadSuccess);
}

if(internalServerError) {
    printStatusResponse(internalServerError);
}

// Enabling submit button only if at least one file is selected
const fileInputIds = ['fileInput-student-data', 'fileInput-course-offerings', 'fileInput-institute-requirements', 'fileInput-course-data'];
const submitBtn = document.getElementById('final-submit-btn');

function checkFiles() {
    const anyFileSelected = fileInputIds.some(id => {
        const input = document.getElementById(id);
        return input && input.files.length > 0;
    });

    submitBtn.disabled = !anyFileSelected;
    submitBtn.classList.toggle('opacity-50', !anyFileSelected);
    submitBtn.classList.toggle('cursor-not-allowed', !anyFileSelected);
}

// Attach listeners to all file inputs
fileInputIds.forEach(id => {
    const input = document.getElementById(id);
    if (input) {
        input.addEventListener('change', checkFiles);
    }
});

// Initial check in case user reloads with a file already selected
checkFiles();

function handleSubmit(event) {
    const button = document.getElementById("final-submit-btn");
    const text = document.getElementById("button-text");
    const spinner = document.getElementById("spinner");
    button.disabled = true;

    text.textContent = "This may take a while!";
    spinner.classList.remove("hidden");
}
