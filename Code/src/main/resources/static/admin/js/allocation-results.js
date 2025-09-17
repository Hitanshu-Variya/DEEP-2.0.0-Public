if(renderResponse) {
    printStatusResponse(renderResponse);
}

function submitWithPath(event) {
    event.preventDefault();
    const sid = document.getElementById("studentId").value.trim();
    const contextPath = document.querySelector('meta[name="context-path"]').getAttribute('content');
    if (sid) {
        window.location.href = `${contextPath}admin/allocation-results/${encodeURIComponent(sid)}`;
    }
}
