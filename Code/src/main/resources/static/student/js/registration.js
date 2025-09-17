document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('requirementsContainer');
    const filtered = instituteRequirements.filter(obj => obj.courseCnt !== null);
    filtered.sort((a, b) => a.category.localeCompare(b.category));

    // Render each
    filtered.forEach(obj => {
        const div = document.createElement('div');
        div.className = "flex items-center";

        div.innerHTML = `
            <label class="text-lg font-semibold text-gray-800 w-16 text-right">${obj.category.toUpperCase()} :</label>
            <input type="text" value="${obj.courseCnt}" readonly
                   class="ml-4 px-3 py-1 border-2 border-gray-300 rounded-lg bg-gray-200 text-center font-medium w-16 cursor-not-allowed" />
        `;
        container.appendChild(div);
    });
});

const values = {};
const container = document.getElementById("categoryInputsContainer");

instituteRequirements.forEach(obj => {
  if (obj.courseCnt != null) {
    const category = obj.category?.toUpperCase();
    const div = document.createElement("div");
    div.className = "flex items-center";

    div.innerHTML = `
      <label class="text-lg font-semibold text-gray-800 w-16 text-right">${category} :</label>
      <input type="number" name="${category}"
        class="ml-4 px-3 py-1 border-2 border-gray-300 rounded-lg bg-white text-center font-medium w-16 appearance-none
        [&::-webkit-outer-spin-button]:appearance-none
        [&::-webkit-inner-spin-button]:appearance-none" />
    `;

    container.appendChild(div);

    // Attach listener after appending so input is in the DOM
    const input = div.querySelector(`input[name="${category}"]`);
    input.addEventListener("input", () => {
      values[category] = input.value.trim() || "0";
    });
  }
});

// Registration Form Steps
let currentStep = 1;
const totalSteps = 3;

function showStep(step) {
    for (let i = 1; i <= totalSteps; i++) {
        const el = document.getElementById(`step-${i}`);
        const progressEL = document.getElementById(`progress-${i}`);
        const progressLine = document.getElementById(`line-${i}`);

        if (progressEL) {
            progressEL.classList.toggle('bg-1321EA', i < step);
            progressEL.classList.toggle('bg-ACCEFF', i > step);

            if (i === step) {
                progressEL.style.boxShadow = 'inset 0 0 0 5px #1321EA';
            } else {
                progressEL.style.boxShadow = '';
            }
        }

        if(progressLine) {
            progressLine.classList.toggle('bg-1321EA', i <= step);
            progressLine.classList.toggle('bg-ACCEFF', i > step);
        }

        if (el) {
            el.classList.toggle('hidden', i !== step);
        }
    }
}

function validateAllSlotsBeforeSubmit() {
    const allSlots = new Set([
        ...Object.keys(skippedSlots),
        ...Object.keys(selectedCoursesBySlot)
    ]);

    for (let slot of allSlots) {
        const isSkipped = skippedSlots[slot] === true;

        if (isSkipped) continue; // user opted to skip this slot

        const selected = selectedCoursesBySlot[slot] || [];
        const total = document.querySelectorAll(`#slot-${slot} .course-row`).length;

        // If user has not selected all courses and not skipped the slot => throw warning
        if (selected.length !== total) {
            showToast(`Please select all courses or confirm no preference for Slot ${slot}.`, statusColors.WARNING);
            return false;
        }
    }

    return true;
}

function CheckInputs() {
    if(currentStep === 2) {
        return validateAllSlotsBeforeSubmit();
    }
    if(currentStep !== 1) {
        return true;
    }

    const element = document.getElementById('step-1');
    const inputs = element.querySelectorAll('input[type="number"]');

    for (let input of inputs) {
        if (input.value.trim() === '') {
            showToast("Please, Fill all 4 requirements First!", statusColors.ERROR);
            return false;
        }
    }

    return true;
}

function nextStep() {
    if (CheckInputs() && currentStep < totalSteps) {
        currentStep++;
        showStep(currentStep);
    }
}

function prevStep() {
    if (currentStep > 1) {
    currentStep--;
    showStep(currentStep);
    }
}

// Registration Form 2
let selectedCoursesBySlot = {};
let skippedSlots = {};
let currentSlot = '';

document.querySelectorAll('#slot-panel').forEach(el => {
    const slot = el.getAttribute('data-slot');
    skippedSlots[slot] = false;
});

// Called when checkbox is toggled
function handleNoSlotCourseCheckbox(checkbox) {
  const slot = currentSlot;
  skippedSlots[slot] = checkbox.checked;

  // Clear selected courses if user opted for none
  if (checkbox.checked) {
    selectedCoursesBySlot[slot] = [];
    updateSelectedCoursesDisplay();
    updateCourseVisibility();
  }
}

// Called when switching slots
function showSlotCourses(slot) {
  slot = String(slot);
  if (slot === currentSlot) return;

  const currentCourses = document.querySelectorAll(`#slot-${currentSlot} .course-row`);
  const selectedInCurrent = selectedCoursesBySlot[currentSlot] || [];
  const totalInCurrent = currentCourses.length;

  const isCurrentSkipped = skippedSlots[currentSlot] === true;
  if (currentStep === 2 && selectedInCurrent.length !== totalInCurrent && !isCurrentSkipped) {
    showToast(`Please select all courses or confirm you dont want any from Slot ${currentSlot}.`, statusColors.WARNING);
    return;
  }

  // Switch slot
  currentSlot = slot;

  // Show/hide slots
  document.querySelectorAll('.slot-courses').forEach(el => el.classList.add('hidden'));
  const slotElement = document.getElementById('slot-' + slot);
  if (slotElement) slotElement.classList.remove('hidden');

  // Update slot button style
  document.querySelectorAll("#slotContainer .slot").forEach(s => {
    s.classList.remove("bg-blue-500");
    s.classList.add("bg-cyan-300");
  });

  const selectedBtn = document.querySelector(`.slot[data-slot="${slot}"]`);
  selectedBtn?.classList.remove("bg-cyan-300");
  selectedBtn?.classList.add("bg-blue-500");

  // Restore checkbox state
  const checkbox = document.getElementById("noSlotCourseCheckbox");
  checkbox.checked = skippedSlots[slot] === true;

  updateSelectedCoursesDisplay();
  updateCourseVisibility();
}

// Hide already selected courses in current slot
function updateCourseVisibility() {
    document.querySelectorAll('.course-row').forEach(courseRow => {
        const courseId = courseRow.getAttribute('data-course-id');
        const slot = courseRow.getAttribute('data-slot');

        const selectedInSlot = selectedCoursesBySlot[slot] || [];
        const isSelected = selectedInSlot.some(course => course.cid === courseId);

        courseRow.style.display = (slot === currentSlot && !isSelected) ? 'grid' : 'none';
    });
}

function addCourseToSelected(cid, slot, name, program, category, credits) {
    if (skippedSlots[slot]) {
        showToast('You have opted out of selecting courses for this slot. Please uncheck the option to select courses again.', statusColors.WARNING);
        return;
    }

    if (!selectedCoursesBySlot[slot]) {
        selectedCoursesBySlot[slot] = [];
    }

    if (selectedCoursesBySlot[slot].find(course => course.cid === cid)) {
        showToast('Course already selected in this slot!', statusColors.WARNING);
        return;
    }

    selectedCoursesBySlot[slot].push({ cid, slot, name, program, category, credits });

    updateSelectedCoursesDisplay();
    updateCourseVisibility();
}

function removeCourseFromSelected(cid) {
    const coursesInSlot = selectedCoursesBySlot[currentSlot] || [];
    selectedCoursesBySlot[currentSlot] = coursesInSlot.filter(course => course.cid !== cid);

    updateSelectedCoursesDisplay();
    updateCourseVisibility();
}

function updateSelectedCoursesDisplay() {
    const selectedCoursesContainer = document.getElementById('selectedCourses');
    const courses = selectedCoursesBySlot[currentSlot] || [];

    if (courses.length === 0) {
        selectedCoursesContainer.innerHTML = '<div class="h-4"></div>';
        return;
    }

    let html = '<div class="h-4"></div>';
    courses.forEach((course, index) => {
        html += `
            <div class="grid grid-cols-8 gap-0 text-xxs md:text-xs lg:text-sm border-b border-gray-300 bg-green-100">
                <div class="p-2 lg:p-3 flex justify-center items-center">
                    <button class="text-white bg-blue-600 hover:bg-blue-700 px-1 py-1 md:px-2 md:py-1 rounded-md text-xxs md:text-xs lg:text-sm font-semibold transition-colors"
                            onclick="removeCourseFromSelected('${course.cid}')">
                        REMOVE
                    </button>
                </div>
                <div class="p-2 lg:p-3 text-center font-medium">${index + 1}</div>
                <div class="p-2 lg:p-3 text-center font-medium">${course.cid}</div>
                <div class="p-2 lg:p-3 col-span-2 font-medium">${course.name}</div>
                <div class="p-2 lg:p-3 text-center font-medium">${course.program}</div>
                <div class="p-2 lg:p-3 text-center font-medium">${course.category}</div>
                <div class="p-2 lg:p-3 text-center font-medium">${course.credits}</div>
            </div>
        `;
    });

    selectedCoursesContainer.innerHTML = html;
}

// Initialization
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.slot[data-slot]').forEach(slotBtn => {
        slotBtn.addEventListener('click', function () {
            const slot = this.getAttribute('data-slot');
            showSlotCourses(slot);
        });
    });

    document.querySelectorAll('.course-btn').forEach(courseBtn => {
        courseBtn.addEventListener('click', function () {
            const cid = this.getAttribute('data-cid');
            const slot = this.getAttribute('data-slot');
            const name = this.getAttribute('data-name');
            const program = this.getAttribute('data-program');
            const category = this.getAttribute('data-category');
            const credits = parseInt(this.getAttribute('data-credits'));
            addCourseToSelected(cid, slot, name, program, category, credits);
        });
    });

    // Set first slot as active initially
    const firstSlot = document.querySelector('.slot[data-slot]');
    if (firstSlot) {
        showSlotCourses(firstSlot.getAttribute('data-slot'));
    }
});

// Registration Form 3
function collectPreferences() {
    const inputs = document.querySelectorAll('.slot-preference-input');
    const preferences = Array.from(inputs).map(input => input.value.trim());
    return preferences;
}

function validateSlotPreferences() {
    const inputs = document.querySelectorAll('.slot-preference-input');
    const preferences = Array.from(inputs).map(input => input.value.trim());

    const maxSlot = inputs.length;
    const seen = new Set();

    for (let i = 0; i < preferences.length; i++) {
        const pref = preferences[i];

        if (pref === '') {
            showToast(`Please fill all ${maxSlot} preferences.`, statusColors.ERROR);
            return false;
        }

        const num = Number(pref);

        if (isNaN(num) || num < 1 || num > maxSlot) {
            showToast(`Preference ${i + 1} must be a number between 1 and ${maxSlot}.`, statusColors.ERROR);
            return false;
        }

        if (seen.has(num)) {
            showToast(`Duplicate preference "${num}" detected. All preferences must be unique.`, statusColors.ERROR);
            return false;
        }

        seen.add(num);
    }

    return true;
}

function getCoursePrefsToCourseMap() {
    const slotCourseMap = {};

    const allSlots = new Set([
        ...Object.keys(skippedSlots),
        ...Object.keys(selectedCoursesBySlot)
    ]);

    for (let slot of allSlots) {
        if (skippedSlots[slot] === true) continue; // Skip this slot

        const selected = selectedCoursesBySlot[slot] || [];

        if (selected.length > 0) {
            // Extract only the cids in the order they appear
            slotCourseMap[slot] = selected.map(course => course.cid);
        }
    }

    return slotCourseMap;
}

document.getElementById('submitButton').addEventListener('click', function () {
  if (validateSlotPreferences()) {
      // If validation passes, show modal
      document.getElementById('confirmModal').classList.remove('hidden');
      document.body.classList.add('backdrop-blur-md', 'overflow-hidden'); // blur background
  }
});

// Confirm button
document.getElementById('confirmSubmit').addEventListener('click', function () {
  document.getElementById('confirmModal').classList.add('hidden');
  document.body.classList.remove('backdrop-blur-md', 'overflow-hidden');

  const submitButton = document.getElementById('submitButton');
  submitButton.disabled = true;
  submitButton.classList.remove('opacity-100', 'cursor-pointer');
  submitButton.classList.add('opacity-50', 'cursor-not-allowed');

  const coursePrefsMapping = getCoursePrefsToCourseMap();
  document.getElementById('studentRequirements').value = JSON.stringify(values);
  document.getElementById('coursePreferences').value = JSON.stringify(coursePrefsMapping);
  document.getElementById('slotPreferences').value = JSON.stringify(collectPreferences());

  document.getElementById('myForm').submit();
});

// Cancel button
document.getElementById('cancelConfirm').addEventListener('click', function () {
  document.getElementById('confirmModal').classList.add('hidden');
  document.body.classList.remove('backdrop-blur-md', 'overflow-hidden');
});
