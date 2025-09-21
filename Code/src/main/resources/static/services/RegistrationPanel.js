export default class RegistrationPanel {
  constructor({ panelId = 'detailsPanel', toastManager = null }) {
    this.panel = document.getElementById(panelId);
    this.contentDiv = this.panel.querySelector('#detailsContent');
    this.toastManager = toastManager; // Optional, for showing messages

    // Attach toggle handler
    this.panel.addEventListener('click', (event) => {
      const toggle = event.target.closest("#toggleRegistration");
      if (!toggle) return;

      this.handleToggle(toggle);
    });
  }

  showDetails(dataDiv) {
    if (!dataDiv) return;
    this.dataDiv = dataDiv;

    const isOpen = dataDiv.dataset.collectionwindowstate.toLowerCase() === 'open';
    const endDate = dataDiv.dataset.enddate || null;

    this.panel.classList.remove('hidden');

    this.contentDiv.innerHTML = `
      <label class="inline-flex items-center mb-6">
        <input type="checkbox" id="toggleRegistration" class="sr-only peer" ${isOpen ? 'checked' : ''}>
        <div class="relative w-14 h-7 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-0.5 after:start-[4px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-6 after:w-6 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600 dark:peer-checked:bg-blue-600"></div>
      </label>

      <h3 class="text-xl font-bold text-[#1E3C72] mb-1">Begin/End Preference Collection</h3>
      <p class="text-xs text-gray-600 mb-4 text-center">Manually toggle Preference Collection window</p>

      <div class="flex flex-col space-y-2 justify-center items-center text-sm font-semibold text-[#1E3C72]">
        <span>${endDate ? `Preference Collection Ends on: ${endDate}` : 'Preference Collection Ended'}</span>

        <div class="flex flex-col sm:flex-row lg:flex-col gap-3 items-center justify-center">
          ${isOpen ? `
            <button onclick="openExtendModal()" type="button" class="ml-4 px-3 py-2 text-sm rounded-lg bg-[#1E3C72] text-white cursor-pointer">Extend Period</button>
            <button type="button" class="ml-4 px-3 py-2 text-sm rounded-lg bg-[#1E3C72] text-white cursor-pointer">Move to Next Phase</button>
            <button type="button" class="ml-4 px-3 py-2 text-sm rounded-lg bg-[#1E3C72] text-white cursor-pointer">Declare Results</button>
          ` : ''}
        </div>
      </div>
    `;
  }

  handleToggle(toggle) {
    if (!this.dataDiv) return;

    const wasChecked = !toggle.checked;

    if (!wasChecked) {
      console.log(this.dataDiv)
      console.log(this.dataDiv.dataset.program)
      console.log(this.dataDiv.dataset.semester)
      document.getElementById("registrationModal").classList.remove("hidden");
    } else {
      document.getElementById("closeRegModal").classList.remove("hidden");
    }

    toggle.checked = wasChecked;
  }

  // Handles opening collection (OK button inside registrationModal)
  handleOpenRegistration(event) {
      event.preventDefault();

      const dateInput = document.getElementById("registration-datepicker");
      const rawValue = dateInput.value.trim();

      if (!rawValue) {
          this.toastManager?.printStatusResponse({
              status: 'WARNING',
              message: "Please select a close date."
          });
          return;
      }

      const parsedDate = new Date(rawValue);
      if (isNaN(parsedDate.getTime())) {
          this.toastManager?.printStatusResponse({
              status: 'ERROR',
              message: "Invalid date selected."
          });
          return;
      }

      const formattedDate = parsedDate.toISOString().split('T')[0];

      const program = this.dataDiv.dataset.program || '';
      const semester = this.dataDiv.dataset.semester || '';

      // Construct URL with query parameters
      const contextPath = document.querySelector('meta[name="context-path"]').getAttribute('content');
      const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
      const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

      const url = `${contextPath}admin/begin-collection?program=${encodeURIComponent(program)}&semester=${encodeURIComponent(semester)}&close-date=${encodeURIComponent(formattedDate)}`;

      // Use fetch to send POST with CSRF header
      fetch(url, {
          method: 'POST',
          headers: { [csrfHeader]: csrfToken }
      })
      .then(res => res.text())
      .then(fragmentHtml => {
          const container = document.getElementById('toast-fragment-container');
          container.innerHTML = fragmentHtml;
      })
      .catch(err => {
          console.error(err);
          this.toastManager?.printStatusResponse({ status: 'ERROR', message: 'Network error.' });
      });
  }

  closeNewRegistrationModal() {
      const modal = document.getElementById("registrationModal");
      const toggleRegistration = document.getElementById("toggleRegistration");
      toggleRegistration.checked = false;
      modal.classList.add("hidden");
  }

  openExtendModal() {
    document.getElementById("extendModal").classList.remove("hidden");
  }

  closeExtendModal() {
    document.getElementById("extendModal").classList.add("hidden");
  }

  handleExtend(event) {
    event.preventDefault();

    const input = document.getElementById("extend-datepicker");
    const rawValue = input.value.trim();

    if (!rawValue) {
      this.toastManager?.printStatusResponse({
        status: 'WARNING',
        message: "Please select a new close date."
      });
      return;
    }

    const parsedDate = new Date(rawValue);
    if (isNaN(parsedDate.getTime())) {
      this.toastManager?.printStatusResponse({
        status: 'ERROR',
        message: "Please select a new close date."
      });
      return;
    }

    const formattedDate = parsedDate.toISOString().split('T')[0];

    const form = event.target;
    const contextPath = document.querySelector('meta[name="context-path"]').getAttribute('content');
    form.action = `${contextPath}admin/extend-period?close-date=${encodeURIComponent(formattedDate)}`;
    form.submit();
  }
}
