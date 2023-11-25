
# Generate base project

## DONE:
##### Split Project into two modules -> Frontend(Pending) and (Backend) core

###### Core (Generate different profiles for environment -> default, dev, prod)

- *** Core (Define different interceptors )
- **** Logging Interceptor (implement aspect cross-cutting concern)
- **** Successful Response wrapper interceptors (have generic response for successful responses)
- **** Failure Response wrapper interceptors (define validation error and custom api error)
- **** Test these interceptors
- *** Implement internationalization for codebase
- *** Take care of security (Authorization(roles) and Authentication )
- - *** setup frontend module [angular setup, registration page, login page,]

## PENDING
- *** rbac (role based access control) authorization
- ***  frontend product (create, edit, view), order (create, edit, view)
- *** start defining domains (product, order)
- report module (backend and frontend)
- performance module
- qr code setup
- user management
- frontend internationalization
- fix h2 dashboard
- refactor auth-block
- theme (dark and light) support
- settings