

Start the Northwind example service

    bazel run //examples/northwind
    
Get all employees

    curl http://localhost:4567/api/employees
    
Create a new employee

    curl -X POST http://localhost:4567/api/employees -H "Content-type: application/json" --data '{"first_name": "John", "last_name": "Smith"}'

