import React from "react";
import styles from "./InputForm.module.css";

const InputForm = () => {
  return (
    <div>
      <form action="" className=" ">
        <div></div>
        <input
          type="text"
          placeholder="Eg. India"
          className={styles.inputSearch}
        />
        <button className={styles.inputSubmit} type="submit">
          Go
        </button>
      </form>
    </div>
  );
};

export default InputForm;
