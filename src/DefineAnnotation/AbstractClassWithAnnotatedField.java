package DefineAnnotation;

public abstract class AbstractClassWithAnnotatedField {

  @FieldAnno(1)
  public int myAnnotatedField;

  public AbstractClassWithAnnotatedField() {
    int value = this.getClass().getFields()[0].getAnnotation(FieldAnno.class).value();

    // System.out.println(value);

    this.myAnnotatedField = value;
  }

}
