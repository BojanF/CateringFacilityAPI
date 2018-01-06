$(document).ready(function(){

  //subscription package details tabs
  function hideSubscriptionPackageDetailsTabs(){
    $("#package-details-tab").hide();
    $("#package-update-tab").hide();
    $("#package-stats-tab").hide();
  }

  $("#package-details").click(function (){
    hideSubscriptionPackageDetailsTabs();
    $("#package-details-tab").show();
  });

  $("#package-update").click(function (){
    hideSubscriptionPackageDetailsTabs();
    $("#package-update-tab").show();
  });

  $("#package-stats").click(function (){
    hideSubscriptionPackageDetailsTabs();
    $("#package-stats-tab").show();
  });
  //subscription package details tabs END

  //beverage-details
  function hideBeverageDetailsTabs(){
    $("#beverage-details-tab").hide();
    $("#beverage-update-tab").hide();
  }

  $("#beverage-details").click(function (){
    hideBeverageDetailsTabs();
    $("#beverage-details-tab").show();
  });

  $("#beverage-update").click(function (){
    hideBeverageDetailsTabs();
    $("#beverage-update-tab").show();
  });
  // beverage details END

  //course details
  function hideCourseDetailsTabs(){
    $("#course-details-tab").hide();
    $("#course-update-tab").hide();
  }

  $("#course-details").click(function (){
    hideCourseDetailsTabs();
    $("#course-details-tab").show();
  });

  $("#course-update").click(function (){
    hideCourseDetailsTabs();
    $("#course-update-tab").show();
  });
  //course details END


  // location update
  function hideLocationUpdateTabs(){
    $("#location-update-tab").hide();
    $("#location-contact-tab").hide();
  }

  $("#location-update").click(function (){
    hideLocationUpdateTabs();
    $("#location-update-tab").show();
  });

  $("#location-contact").click(function (){
    hideLocationUpdateTabs();
    $("#location-contact-tab").show();
  });
  //location update end
});
