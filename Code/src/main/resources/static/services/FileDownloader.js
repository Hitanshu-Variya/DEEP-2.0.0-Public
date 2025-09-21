export default class FileDownloader {
  constructor({ contextPath = '', toastManager, status }) {
    this.contextPath = contextPath;
    this.toastManager = toastManager;
    this.status = status || { OK: 200, ERROR: 'error' };
  }

  attach(downloadBtns, getUrlCallback) {
    downloadBtns.forEach(btn => {
      btn.addEventListener('click', async (e) => {
        e.preventDefault();

        const downloadUrl = getUrlCallback(btn);
        if (!downloadUrl) return;

        this._downloadFile(btn, downloadUrl);
      });
    });
  }

  async _downloadFile(btn, downloadUrl) {
    const originalText = btn.innerHTML;
    btn.innerHTML = `
      <span class="animate-spin inline-block w-4 h-4 border-2 border-white border-t-transparent rounded-full mr-2"></span>
      Downloading...`;
    btn.disabled = true;

    try {
      const res = await fetch(downloadUrl);

      if (res.status !== this.status.OK) {
        const errorText = await res.text();
        this.toastManager.printStatusResponse({
          status: this.status.ERROR,
          message: errorText || 'Download failed'
        });
        return;
      }

      const blob = await res.blob();
      const contentDisposition = res.headers.get("Content-Disposition");
      let filename = "downloaded_file";

      if (contentDisposition && contentDisposition.includes("filename=")) {
        const match = contentDisposition.match(/filename="?([^"]+)"?/);
        if (match && match[1]) filename = match[1];
      } else {
        filename = downloadUrl.split("/").pop() || filename;
      }

      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);

    } catch (err) {
      this.toastManager.printStatusResponse({
        status: this.status.ERROR,
        message: "Network error: Could not download file"
      });
    } finally {
      btn.innerHTML = originalText;
      btn.disabled = false;
    }
  }
}
