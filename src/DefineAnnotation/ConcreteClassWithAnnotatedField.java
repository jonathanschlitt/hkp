package DefineAnnotation;

public class ConcreteClassWithAnnotatedField extends AbstractClassWithAnnotatedField {

  public ConcreteClassWithAnnotatedField() {
    super();
  }

  public static void main(String[] args) {
    ConcreteClassWithAnnotatedField ccwaf = new ConcreteClassWithAnnotatedField();
    System.out.println(ccwaf.myAnnotatedField);
  }

}
