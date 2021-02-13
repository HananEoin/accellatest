package accella.test.accellatest.services;

import accella.test.accellatest.entities.Person;
import accella.test.accellatest.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CommandLine implements CommandLineRunner {
    @Autowired
    private PersonRepository personRepository;

    private final String help = "The following options are available" +
            "\nAdd {firstname} {surname} - Creates new person" +
            "\nEdit {id} {firstname} {surname} - Edits previously created person" +
            "\nDelete {id} - Deletes Person" +
            "\nCount - Returns count of current people" +
            "\nList - Lists all current people in System";

    @Override
    public void run(String... args) throws Exception {
        boolean finished = false;

        System.out.println("\n\n\n");
        System.out.println("Welcome to Person Manager");
        System.out.println("--------------------------");

        System.out.println("Enter commands below:");

        while(!finished){
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            finished = parse(input);
        }
        System.out.println("Done");
    }

    private boolean parse(String input){
        String command = input.split(" ")[0].toLowerCase();

        if (command.equals("")){
            return false;
        }


        switch (command) {
            case "add":
                addPerson(input);
                break;
            case "edit":
                editPerson(input);
                break;
            case "delete":
                deletePerson(input);
                break;
            case "count":
                getPersonCount();
                break;
            case "list":
                getList();
                break;
            case "import":

                break;
            case "done":
                return true;
            default:
                System.out.print(help);
                ;
        }
        return false;
    }

    private void addPerson(String input){
        String[] splitInput = input.split(" ");
        if (splitInput.length != 3){
            System.out.println("Please include the the name and surname of the person you wish to add");
            return;
        }
        personRepository.save(new Person(splitInput[1], splitInput[2]));
        System.out.println("New person added successfully!");
    }

    private void editPerson(String input){
        String[] splitInput = input.split(" ");
        int id = -1;

        if (splitInput.length != 4){
            System.out.println("Please include the the name and surname of the person you wish to add");
            return;
        }
        try {
            id = Integer.parseInt(splitInput[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid id given");
        }
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()){
            Person person = optionalPerson.get();
            person.setFirstName(splitInput[2]);
            person.setSurname(splitInput[3]);
            personRepository.save(person);
            System.out.println("Edit successful!");
        }
        else{
            System.out.println("No person found with given id");
        }
    }

    private void deletePerson(String input){
        String[] splitInput = input.split(" ");
        try {
            int id = Integer.parseInt(splitInput[1]);
            Optional<Person> optionalPerson = personRepository.findById(id);
            if (optionalPerson.isPresent()){
                personRepository.deleteById(id);
                System.out.println("Delete successful!");
            }
            else
            {
                System.out.println("No person found with given id");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid id given");
        }
    }

    private void getPersonCount(){
        System.out.println("Current person count: " + personRepository.count());
    }

    private void getList(){
        List<Person> personList = personRepository.findAll();
        System.out.println("ID\tFirst Name\tSurname");
        for (Person person: personList){
            System.out.println(person.getId() + "\t" + person.getFirstName() + "\t" + person.getSurname());
        }
    }
}
