http://localhost:4000/api/v2/api-docs/

En Authorization añadir Bearer + el token sin comillas. IMPORTANTE AÑADIR LA PALABRA Bearer

curl -X GET "http://localhost:4000/api/gestiones" -H  "accept: application/json" -H  "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImIxNzg0NGI5LTY1MTktNDFjNS05ZDk1LTIxNjlmZjMzMGNlMiIsInVzZXJuYW1lIjoidXNlciIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NTkzMzUwOTMsImV4cCI6MTY1OTMzNTM5M30.uv_v2Xv-fJSXqoa9C3TvJ8bZvpbfq_sanBjTCEQBtN8"

