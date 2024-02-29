class Employee:
    def __init__(self, name):
        self.name = name
        self.reports = {}


class Organization:
    def __init__(self, lines):
        self.ceo = None
        employees = {}
        unparented = set()
        for line in lines:
            names = line.split(',')
            manager_name = names[0]
            if manager_name not in employees:
                employees[manager_name] = Employee(manager_name)
                unparented.add(manager_name)
            manager = employees[manager_name]
            for report_name in names[1:]:
                if report_name not in employees:
                    employees[report_name] = Employee(report_name)
                report = employees[report_name]
                manager.reports[report_name] = report
                if report_name in unparented:
                    unparented.remove(report_name)
        if len(unparented) != 1:
            raise Exception("Multiple CEOs: " + str(unparented))
        for ceo in unparented:
            self.ceo = employees[ceo]

    def print(self):
        def traverse_and_print(level, employee):
            chars = []
            for i in range(level):
                chars.append("....")
            chars.append(employee.name)
            print(''.join(chars))
            for report in employee.reports.values():
                traverse_and_print(level+1, report)
        traverse_and_print(0, self.ceo)


testcase = ['Beck,Eli,Frankie',
            'Ash,Beck,Cooper,Dakota',
            'Dakota,Ginger,Indiana',
            'Ginger,Holden']


Organization(testcase).print()
