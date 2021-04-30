package android.exercise.mini.calculator.app;

import java.io.Serializable;
import java.util.ArrayList;

public class SimpleCalculatorImpl implements SimpleCalculator {

  // todo: add fields as needed
  private int output_total;
  private Integer arrInd;
  private ArrayList<String> stringsArr= new ArrayList<>();
  public SimpleCalculatorImpl()
  {
    this.stringsArr.add("0");
    this.output_total = 0;
    this.arrInd = 1;

  }


  @Override
  public String output() {
    // todo: return output based on the current state
    //return "implement me please";
    if(arrInd == 1 && stringsArr.get(0).equals("0")) {
      return Integer.toString(this.output_total);
    }
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < stringsArr.size(); i++) {
      sb.append(stringsArr.get(i));
    }

    return sb.toString();

  }

  @Override
  public void insertDigit(int digit) {
    // todo: insert a digit
    if(arrInd == 1 && stringsArr.get(0).equals("0"))
    {
      stringsArr.remove(0);
      arrInd -= 1;
    }
    if((digit < 10) && (digit >= 0)) {
      stringsArr.add(Integer.toString(digit));
      this.arrInd += 1;

    }
    else
    {
     throw new IllegalArgumentException();
    }
  }

  @Override
  public void insertPlus() {
    // todo: insert a plus
    if(stringsArr.get(arrInd - 1).equals("+") || stringsArr.get(arrInd - 1).equals("-")) {
      return;
    }
    stringsArr.add("+");
    this.arrInd += 1;
  }

  @Override
  public void insertMinus() {
    // todo: insert a minus
    if(stringsArr.get(arrInd - 1).equals("+") || stringsArr.get(arrInd - 1).equals("-")) {
      return;
    }
    stringsArr.add("-");
    this.arrInd += 1;

  }

  @Override
  public void insertEquals() {
    // todo: calculate the equation. after calling `insertEquals()`, the output should be the result
    //  e.g. given input "14+3", calling `insertEquals()`, and calling `output()`, output should be "17"


//    if(stringsArr.get(arrInd - 1).equals("+") || stringsArr.get(arrInd - 1).equals("-")) {
//      deleteLast();
//    }
    ArrayList<String> arr_temp = new ArrayList<>();
    StringBuilder cur_num = new StringBuilder("0");
    for(int i = 0; i< arrInd; i++) {
      switch (stringsArr.get(i)) {
        case "+":
          arr_temp.add(cur_num.toString());
          cur_num = new StringBuilder();
          break;
        case "-":
          arr_temp.add(cur_num.toString());
          cur_num = new StringBuilder();
          cur_num.append(stringsArr.get(i));
          break;
        default:
          cur_num.append(stringsArr.get(i));
      }

    }
    arr_temp.add(cur_num.toString());
    int k = 0;
    while(k < arr_temp.size())
    {
      int cur =Integer.parseInt(arr_temp.get(k));
      output_total += cur;
      k ++;
    }
    stringsArr.clear();
    String num = Integer.toString(output_total);
    stringsArr.add(num);
    output_total = 0;
    arrInd = 1;

  }

  @Override
  public void deleteLast() {
    // todo: delete the last input (digit, plus or minus)
    //  e.g.
    //  if input was "12+3" and called `deleteLast()`, then delete the "3"
    //  if input was "12+" and called `deleteLast()`, then delete the "+"
    //  if no input was given, then there is nothing to do here
    int index = stringsArr.size() - 1;
    if(stringsArr.isEmpty()){
      stringsArr.add("0");
      return;
    }
    stringsArr.remove(index);
    this.arrInd -= 1;

  }

  @Override
  public void clear() {
    // todo: clear everything (same as no-input was never given)
    stringsArr.clear();
    stringsArr.add("0");
    output_total = 0;
    arrInd = 1;
  }

  @Override
  public Serializable saveState() {
    CalculatorState state = new CalculatorState();
    state.set(stringsArr, output_total, arrInd);
    // todo: insert all data to the state, so in the future we can load from this state
    return state;
  }

  @Override
  public void loadState(Serializable prevState) {
    if (!(prevState instanceof CalculatorState)) {
      return; // ignore
    }
    CalculatorState casted = (CalculatorState) prevState;
    stringsArr = casted.get_arr();
    output_total = casted.get_output();
    arrInd = casted.get_arrInd();
    // todo: use the CalculatorState to load
  }

  private static class CalculatorState implements Serializable {
    /*
    TODO: add fields to this class that will store the calculator state
    all fields must only be from the types:
    - primitives (e.g. int, boolean, etc)
    - String
    - ArrayList<> where the type is a primitive or a String
    - HashMap<> where the types are primitives or a String
     */

    private ArrayList<String> stringsArr_sr= new ArrayList<>();
    private int output_total_sr;
    private int arrInd_sr;
    public void set(ArrayList<String> arrStr, int output_total, int arrInd)
    {
      this.stringsArr_sr = new ArrayList<>(arrStr);
      this.output_total_sr = output_total;
      this.arrInd_sr = arrInd;

    }

    public ArrayList<String> get_arr()
    {
      return stringsArr_sr;
    }

    public int get_output()
    {
      return this.output_total_sr;
    }
    public int get_arrInd()
    {
      return arrInd_sr;
    }
  }
}
