import React from "react";
import { useEffect, useState } from "react";
import { TwitterPicker } from "react-color";
import styled from "styled-components";

const ThemeColor: React.FC<{
  showColorPicker: boolean;
  setShowColorPicker: React.Dispatch<React.SetStateAction<boolean>>;
  handleChange: Function;
}> = ({ showColorPicker, setShowColorPicker }) => {
  const savedColor = localStorage.getItem("mainColor");
  const [mainColor, setMainColor] = useState<string>(
    savedColor ? savedColor : "#9747ff"
  );

  const root: HTMLElement = document.documentElement;

  const handleChange = (mainColor: { hex: string }) => {
    const selectedColor: string = mainColor.hex;
    setMainColor(selectedColor);
  };

  const handleReset = () => {
    const defaultColor = "#9747ff";
    setMainColor(defaultColor);
    root.style.setProperty("--main-color", defaultColor);
    root.style.setProperty("--lightpurple-color", "#b18cff");
    localStorage.setItem("mainColor", defaultColor);
  };

  const handleSave = () => {
    localStorage.setItem("mainColor", mainColor);
    root.style.setProperty("--main-color", mainColor);
    toggleColorPicker(true);
  };

  const handleCancel = () => {
    const savedColor = localStorage.getItem("mainColor");
    root.style.setProperty("--main-color", savedColor);
    toggleColorPicker(true);
  };

  const toggleColorPicker = (isChange: boolean) => {
    if (isChange) setShowColorPicker(!showColorPicker);
  };

  const confirmBeforeUnload = (e: BeforeUnloadEvent) => {
    if (mainColor !== localStorage.getItem("mainColor")) {
      e.returnValue =
        "변경된 색상이 저장되지 않을 수 있습니다. 계속하시겠습니까?";
    }
  };

  useEffect(() => {
    root.style.setProperty("--main-color", mainColor);
    window.addEventListener("beforeunload", confirmBeforeUnload);
    return () => {
      window.removeEventListener("beforeunload", confirmBeforeUnload);
    };
  }, [mainColor]);

  return (
    <ThemeColorStyled>
      <TableTopStyled>
        <TableTitleStyled>테마 컬러</TableTitleStyled>
        {showColorPicker ? (
          <>
            <BtnWrapStyled>
              <SaveBtnStyled onClick={handleSave}>저장</SaveBtnStyled>
              <ResetBtnStyled onClick={handleCancel}>취소</ResetBtnStyled>
            </BtnWrapStyled>
          </>
        ) : (
          <ResetBtnStyled onClick={handleReset}>초기화</ResetBtnStyled>
        )}
      </TableTopStyled>
      <TableBodyStyled>
        <ColorSelectStyled>
          메인 컬러
          <MainColorButtonStyled
            onClick={() => toggleColorPicker(!showColorPicker)}
          />
        </ColorSelectStyled>
        {showColorPicker && (
          <TwitterPicker color={mainColor} onChangeComplete={handleChange} />
        )}
      </TableBodyStyled>
    </ThemeColorStyled>
  );
};

const ThemeColorStyled = styled.div`
  width: 350px;
  height: 215px;
  background-color: var(--lightgary-color);
  border-radius: 10px;
  padding: 30px;
`;

const TableTitleStyled = styled.div`
  font-size: 18px;
  font-weight: bold;
`;

const TableTopStyled = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
`;

const BtnWrapStyled = styled.div`
  display: flex;
`;

const ResetBtnStyled = styled.div`
  width: 54px;
  height: 23px;
  background-color: white;
  border-radius: 4px;
  color: var(--gray-color);
  font-size: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const SaveBtnStyled = styled.div`
  width: 54px;
  height: 23px;
  background-color: var(--main-color);
  border-radius: 4px;
  color: white;
  font-size: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
`;

const MainColorButtonStyled = styled.button`
  width: 28px;
  height: 28px;
  background-color: var(--main-color);
  border-radius: 8px;
`;

const ColorSelectStyled = styled.div`
  padding: 20px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const TableBodyStyled = styled.div`
  background-color: white;
  width: 290px;
  height: 120px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export default ThemeColor;
