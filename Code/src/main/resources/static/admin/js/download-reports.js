let selectedSemester = null;
function HandleSemesterSelection(semesterBtns, downloadBtns, semesterInputs) {
    semesterBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            // Remove active state from all buttons
            semesterBtns.forEach(b => {
                b.classList.remove('bg-2D9D5D');
                b.classList.add('bg-1E3C72');
            });

            // Add active state to clicked button
            this.classList.remove('bg-1E3C72');
            this.classList.add('bg-2D9D5D');

            selectedSemester = this.dataset.sem;

            // Update all hidden semester inputs
            semesterInputs.forEach(input => {
                input.value = selectedSemester;
            });

            // Enable download buttons
            downloadBtns.forEach(downloadBtn => {
                downloadBtn.disabled = false;
                downloadBtn.classList.remove('opacity-50', 'cursor-not-allowed');
                downloadBtn.classList.add('cursor-pointer');
            });
        });
    });
}

function InitializeDownloadButtons(resultDownloadBtns) {
    if (selectedSemester) {
        resultDownloadBtns.forEach(btn => {
            btn.disabled = false;
        });

        document.querySelectorAll('.result-semester-input').forEach(input => {
            input.value = selectedSemester;
        });
    }
}

function HandleDownloadButtonClick(downloadBtns, checkforSemester = true) {
    downloadBtns.forEach(btn => {
        btn.addEventListener('click', async function (e) {
            e.preventDefault();

            if (checkforSemester && !selectedSemester) {
                showToast('Please select a semester first!', statusColors.ERROR);
                return;
            }

            const form = this.closest('form');
            const name = form.querySelector('input[name="name"]').value;
            const semester = checkforSemester ? selectedSemester : '';
            const contextPath = document.querySelector('meta[name="context-path"]').getAttribute('content');
            const downloadUrl = checkforSemester
                ? `${contextPath}admin/download-reports/${semester}/${name}`
                : `${contextPath}admin/download-reports/${name}`;

            // Show loading state
            const originalText = this.innerHTML;
            this.innerHTML = `
                <span class="animate-spin inline-block w-4 h-4 border-2 border-white border-t-transparent rounded-full mr-2"></span>
                Downloading...`;
            this.disabled = true;

            try {
                const res = await fetch(downloadUrl);

                if (res.status !== status.OK) {
                    const errorText = await res.text();
                    printStatusResponse(res.status, errorText);
                } else {
                    const blob = await res.blob();
                    const contentDisposition = res.headers.get("Content-Disposition");

                    // Extract filename
                    let filename = "downloaded_file";
                    if (contentDisposition && contentDisposition.includes("filename=")) {
                        const match = contentDisposition.match(/filename="?([^"]+)"?/);
                        if (match && match[1]) filename = match[1];
                    } else {
                        filename = downloadUrl.split("/").pop() || filename;
                    }

                    // Trigger download
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement("a");
                    a.href = url;
                    a.download = filename;
                    document.body.appendChild(a);
                    a.click();
                    a.remove();
                    window.URL.revokeObjectURL(url);
                }
            } catch (err) {
                console.error("Fetch error:", err);
                showToast("Something went wrong due to Network Error. Please contact support.", statusColors.ERROR);
            } finally {
                // Always restore button after fetch resolves or fails
                this.innerHTML = originalText;
                this.disabled = false;
            }
        });
    });
}

document.addEventListener('DOMContentLoaded', function() {
    // Handle Input Data
    const inputDownloadBtns = document.querySelectorAll('.input-download-btn');
    HandleDownloadButtonClick(inputDownloadBtns, false);

    // Handle result files
    const resultSemesterBtns = document.querySelectorAll('.result-semester-btn');
    const resultDownloadBtns = document.querySelectorAll('.result-download-btn');
    const resultSemesterInputs = document.querySelectorAll('.result-semester-input');

    HandleSemesterSelection(resultSemesterBtns, resultDownloadBtns, resultSemesterInputs);
    InitializeDownloadButtons(resultDownloadBtns);
    HandleDownloadButtonClick(resultDownloadBtns);
});