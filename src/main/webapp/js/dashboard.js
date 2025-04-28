
document.addEventListener("DOMContentLoaded", function () {
  const mainLinks = document.querySelectorAll(".links .link");
  const subLinks = document.querySelectorAll(".dropdown .sublink a");
  const settingLinks = document.querySelectorAll(".sdropdown .sublink-setting a");

  const dropdownToggle = document.getElementById("dropdown-toggle");
  const settingToggle = document.getElementById("setting-toggle");

  const complaintsDropdown = document.getElementById("complaints-dropdown");
  const settingsDropdown = document.getElementById("setting-dropdown");

  const downArrowComplaint = document.getElementById("down-arrow-complaint");
  const upArrowComplaint = document.getElementById("up-arrow-complaint");

  const downArrowSetting = document.getElementById("down-arrow-setting");
  const upArrowSetting = document.getElementById("up-arrow-setting");

  // First, remove all 'active' classes from main and sub links
  mainLinks.forEach(link => link.classList.remove("active"));
  subLinks.forEach(link => link.classList.remove("active"));
  settingLinks.forEach(link => link.classList.remove("active"));
  dropdownToggle.classList.remove("active");
  settingToggle.classList.remove("active");

  complaintsDropdown.style.display = "none";
  settingsDropdown.style.display = "none";
  downArrowComplaint.style.display = "inline";
  upArrowComplaint.style.display = "none";
  downArrowSetting.style.display = "inline";
  upArrowSetting.style.display = "none";

  // Dropdown toggle logic
  dropdownToggle.addEventListener("click", function () {
    const isOpen = complaintsDropdown.style.display === "flex";
    complaintsDropdown.style.display = isOpen ? "none" : "flex";
    dropdownToggle.classList.toggle("active", !isOpen);
    downArrowComplaint.style.display = isOpen ? "inline" : "none";
    upArrowComplaint.style.display = isOpen ? "none" : "inline";
  });

  settingToggle.addEventListener("click", function () {
    const isOpen = settingsDropdown.style.display === "flex";
    settingsDropdown.style.display = isOpen ? "none" : "flex";
    settingToggle.classList.toggle("active", !isOpen);
    downArrowSetting.style.display = isOpen ? "inline" : "none";
    upArrowSetting.style.display = isOpen ? "none" : "inline";
  });

  // URL-based active link logic
  const currentPage = window.location.pathname.split("/").pop();

  // Main menu
  mainLinks.forEach(link => {
    const aTag = link.querySelector("a");
    if (aTag && aTag.getAttribute("href") === currentPage) {
      link.classList.add("active");
    }
  });

  // My Complaint sublinks
  subLinks.forEach(link => {
    if (link.getAttribute("href") === currentPage) {
      link.classList.add("active");
      dropdownToggle.classList.add("active");
      complaintsDropdown.style.display = "flex";
      downArrowComplaint.style.display = "none";
      upArrowComplaint.style.display = "inline";
    }
  });

  // Settings sublinks
  settingLinks.forEach(link => {
    if (link.getAttribute("href") === currentPage) {
      link.classList.add("active");
      settingToggle.classList.add("active");
      settingsDropdown.style.display = "flex";
      downArrowSetting.style.display = "none";
      upArrowSetting.style.display = "inline";
    }
  });
});
