# Marriage Harmony API

## Folder Structure

```bash
marriage-harmony-api
├── src
│   ├── main
│   │   ├── java/com/shadi
│   │   │   ├── IwShadiApplication.java
│   │   │   ├── Config
│   │   │   │   ├── CustomAccessDeniedHandler.java
│   │   │   │   ├── CustomAuthentication.java
│   │   │   │   ├── JwtAuthenticationEntryPoint.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   ├── JwtHelpers.java
│   │   │   │   └── ShadiConfiguration.java
│   │   │   ├── Controllers
│   │   │   │   ├── ImageUploadController.java
│   │   │   │   ├── SearchDetailsController.java
│   │   │   │   ├── UserFamilyDetailsController.java
│   │   │   │   ├── UserLifeStyleAndEducationController.java
│   │   │   │   ├── UserPartnerPreferencesController.java
│   │   │   │   ├── UserPersonalDetailsController.java
│   │   │   │   ├── UserProfileController.java
│   │   │   │   └── ViewProfilesController.java
│   │   │   ├── entity
│   │   │   │   ├── SearchDetails.java
│   │   │   │   ├── UserFamilyDetails.java
│   │   │   │   ├── UserImage.java
│   │   │   │   ├── UserLifeStyleAndEducation.java
│   │   │   │   ├── UserPartnerPreferences.java
│   │   │   │   └── UserPersonalDetails.java
│   │   │   ├── exception
│   │   │   │   ├── handle
│   │   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── AccessDeniedException.java
│   │   │   │   ├── CustomException.java
│   │   │   │   ├── GenericException.java
│   │   │   │   ├── InternalServerError.java
│   │   │   │   └── NotFoundException.java
│   │   │   ├── profile
│   │   │   │   ├── dto
│   │   │   │   │   ├── ChangePasswordDto.java
│   │   │   │   │   ├── ImageUploadDto.java
│   │   │   │   │   └── UserRegistrationProfileDto.java
│   │   │   │   ├── entity
│   │   │   │   │   ├── UserProfile.java
│   │   │   │   │   ├── UserRegistrationProfile.java
│   │   │   │   │   └── UserRoles.java
│   │   │   ├── records
│   │   │   │   ├── AllUserRecord.java
│   │   │   │   └── ProfileRecords.java
│   │   │   ├── repo
│   │   │   │   ├── SearchDetailsRepo.java
│   │   │   │   ├── UserFamilyDetailsRepo.java
│   │   │   │   ├── UserImageRepo.java
│   │   │   │   ├── UserLifeStyleAndEducationRepo.java
│   │   │   │   ├── UserPartnerPreferencesRepo.java
│   │   │   │   ├── UserPersonalDetailsRepo.java
│   │   │   │   └── UserProfileRegistrationRepo.java
│   │   │   ├── service
│   │   │   │   ├── impl
│   │   │   │   │   ├── SearchDetailsServiceImpl.java
│   │   │   │   │   ├── UserFamilyDetailsServiceImpl.java
│   │   │   │   │   ├── UserLifeStyleAndEducationServiceImpl.java
│   │   │   │   │   ├── UserPartnerPreferencesServiceImpl.java
│   │   │   │   │   ├── UserPersonalDetailsServiceImpl.java
│   │   │   │   │   ├── UserRegistrationServiceImpl.java
│   │   │   │   │   ├── UserServiceImpl.java
│   │   │   │   │   └── ViewProfilesServiceImpl.java
│   │   │   │   ├── SearchDetailsService.java
│   │   │   │   ├── UserFamilyDetailsService.java
│   │   │   │   ├── UserLifeStyleAndEducationService.java
│   │   │   │   ├── UserPartnerPreferencesService.java
│   │   │   │   ├── UserPersonalDetailsService.java
│   │   │   │   ├── UserRegistrationService.java
│   │   │   │   ├── ImageUploadService.java
│   │   │   │   ├── UserService.java
│   │   │   │   └── ViewAllService.java
│   │   │   ├── utils
│   │   │   │   ├── AppConstants.java
│   │   │   │   ├── ConstantValues.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   ├── ExtraResponse.java
│   │   │   │   ├── JavaMailUtil.java
│   │   │   │   ├── RandomPasswordGenerator.java
│   │   │   │   ├── ResponseStructure.java
│   │   │   │   └── StringListConverter.java
│   │   │   └── IwShadiApplication.java
│   │   └── resources
│   │       ├── static/img
│   │       │   └── email.png
│   │       │   └── templates
│   │       │       └── passwordSender.html
│   │       └── application.properties
│   ├── test/java/com/shadi
│   │   └── IwShadiApplicationTests.java
├── target
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```
